package aeon.extensions.reporting;

import aeon.core.common.helpers.StringUtils;
import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.reportmodel.FailedExpectation;
import aeon.extensions.reporting.reportmodel.Result;
import aeon.extensions.reporting.reportmodel.ResultReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.http.HttpHeaders.USER_AGENT;

class ReportSummary {

    private IConfiguration aeonConfiguration;
    private IConfiguration pluginConfiguration;
    private SlackBot slackBot;
    private boolean displayClassName;
    private int errorMessageCharLimit;

    private static Logger log = LogManager.getLogger(ReportSummary.class);

    ReportSummary(IConfiguration pluginConfiguration, IConfiguration aeonConfiguration) {
        this.aeonConfiguration = aeonConfiguration;
        this.pluginConfiguration = pluginConfiguration;
        this.slackBot = new SlackBot(pluginConfiguration);
        this.displayClassName = pluginConfiguration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.errorMessageCharLimit = (int) pluginConfiguration.getDouble(ReportingConfiguration.Keys.ERROR_MESSAGE_CHARACTER_LIMIT, 300);
    }

    void createReportFile(Report report) {
        ResultReport resultReport = new ResultReport();
        resultReport.counts.passed = report.getPassed();
        resultReport.counts.failed = report.getFailed();
        resultReport.counts.disabled = report.getSkipped();
        resultReport.timer.duration = report.getTotalTime();
        for (Scenario scenario: report.getScenarioBeans()) {
            Result result = new Result();
            result.description = scenario.getScenarioName();
            result.duration = getTime(scenario.getEndTime() - scenario.getStartTime()).replace(" seconds", "s");
            result.status = scenario.getStatus().toLowerCase();
            if (result.status.equals("skipped")) {
                result.status = "disabled";
            }

            if (result.status.equals("failed")) {
                FailedExpectation failedExpectation = new FailedExpectation();
                failedExpectation.message = scenario.getErrorMessage();
                failedExpectation.stack = scenario.getStackTrace();
                result.failedExpectations.add(failedExpectation);

                Image screenshot = scenario.getScreenshot();
                if (screenshot != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        ImageIO.write((BufferedImage) screenshot, "png", stream);
                        String data = DatatypeConverter.printBase64Binary(stream.toByteArray());
                        result.screenshotPath = "data:image/png;base64," + data;
                    } catch (IOException e) {
                        log.warn("Could not write screenshot", e);
                    }
                }
            }

            resultReport.sequence.add(result);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(resultReport);

        } catch (JsonProcessingException e) {
            log.error("Could not write JSON results", e);

            return;
        }

        String reportTemplate;
        try (InputStream scriptReader = ReportingPlugin.class.getResourceAsStream("/report.tmpl.html")) {
            reportTemplate =  new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            log.error("File not found on path");

            return;
        } catch (IOException e) {
            log.error("Problem reading from file");

            return;
        }

        json = json.replace("\\n", "\\\\n")
                .replace("\\t", "\\\\t")
                .replace("\\\"", "\\\\\\\"");
        String script = "<script>RESULTS.push(JSON.parse('" + json + "'));</script>";
        reportTemplate = reportTemplate.replace("<!-- inject::scripts -->", script);

        String fileName = pluginConfiguration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "")
            + "/reports-" + ManagementFactory.getRuntimeMXBean().getName().split("@")[0] + ".html";

        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportTemplate);
        } catch (FileNotFoundException e) {
            log.error("File not found on path");
        }
    }

    void sendSummaryReport(Report reportBean) {

        String title = "Automation Report - " + ReportingPlugin.reportDateFormat.format(reportBean.getScenarioBeans().get(0).getStartTime()).replace(":", "-");

        String slackChannel1 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
        String slackChannel2 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
        if (StringUtils.isBlank(slackChannel1) && StringUtils.isBlank(slackChannel2)) {
            log.info("No Slack channel is set up.");

            return;
        }

        if (StringUtils.isNotBlank(slackChannel1)) {
            slackBot.publishMessageToSlack(this.summaryReport(reportBean, title), slackChannel1);
            Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");
        }

        if (StringUtils.isNotBlank(slackChannel2)) {
            Boolean failed = false;
            if (reportBean.getPassed() != reportBean.getTotal()) {
                failed = true;
            }

            if (failed) {
                String message = "<!here> - There are test failures. Please see attached report below.";
                slackBot.publishNotificationToSlack(slackChannel2, message);
                slackBot.publishMessageToSlack(this.summaryReport(reportBean, title), slackChannel2);
                Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");
            } else {
                String message = "Tests Passed for URL: " + aeonConfiguration.getString("aeon.environment", "") + " starting at " + ReportingPlugin.getTime();
                slackBot.publishNotificationToSlack(slackChannel2, message);
            }
        }
    }

    private File summaryReport(Report reportBean, String title) {
        String htmlBody = "";
        htmlBody = htmlBody + this.getHead();
        String[] successHeaders;
        String[] failureHeaders;
        if (displayClassName) {
            successHeaders = new String[]{"Class Name", "Test Name", "Status"};
            failureHeaders = new String[]{"Class Name", "Test Name", "Status", "Error Message"};
        } else {
            successHeaders = new String[]{"Test Name", "Status"};
            failureHeaders = new String[]{"Test Name", "Status", "Error Message"};
        }

        //Job Info
        htmlBody = htmlBody + this.createTable(new String[]{"Test Configuration"}, this.getJobInformation(), "t02");
        if (ReportingPlugin.suiteName == null) {
            //Suite Summary for JUnit so no Suite column
            htmlBody = htmlBody + createHeader("Overall Summary") +
                    createTable(new String[]{"Total Tests", "Passed", "Failed",
                            "Skipped", "Total Time"}, getTableBodyForSuiteSummaryJUnit(reportBean), "t01");
        } else {
            //Suite Summary for TestNG
            htmlBody = htmlBody + createHeader("Overall Summary") +
                    createTable(new String[]{"Suite Name", "Total Tests", "Passed", "Failed",
                            "Broken", "Skipped", "Total Time"}, getTableBodyForSuiteSummary(reportBean), "t01");
        }
        //Broken Table
        if (reportBean.isSuiteFailed() || reportBean.isSuiteBroken() || reportBean.isSuiteSkipped()) {
            htmlBody = htmlBody + createHeader("Failed List")
                    + createTable(failureHeaders,
                    getTableBodyForFailedList(reportBean.getScenarioBeans(), "FAILED")
                            + getTableBodyForFailedList(reportBean.getScenarioBeans(), "BROKEN")
                            + getTableBodyForFailedList(reportBean.getScenarioBeans(), "SKIPPED"), "t03");
        }

        //Pass List
        if (reportBean.isSuitePassed()) {
            htmlBody = htmlBody + createHeader("Pass List")
                    + createTable(successHeaders,
                    getTableBodyForPassList(reportBean.getScenarioBeans()), "t04") + "<br>";
        }

        return Utils.htmlToPngFile(htmlBody, Utils.getResourcesPath() + title + ".png");
    }

    private String createTable(String[] headers, String tableBody, String id) {
        StringBuilder finalHeader = new StringBuilder();
        for (String header : headers) {
            finalHeader.append(this.createHeaderColumn(header));
        }
        return this.createTable(finalHeader + tableBody, id);
    }

    private String getTableBodyForPassList(List<Scenario> scenarios) {
        StringBuilder finalBody = new StringBuilder();

        for (Scenario scenario : scenarios) {
            if (scenario.getStatus().equalsIgnoreCase("PASSED")) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(scenario.getModuleName());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(scenario.getScenarioName())
                                + createColumnAndAssignColor(scenario.getStatus())
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForFailedList(List<Scenario> scenarios, String status) {
        StringBuilder finalBody = new StringBuilder();

        for (Scenario scenario : scenarios) {
            if (scenario.getStatus().equalsIgnoreCase(status)) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(scenario.getModuleName());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(scenario.getScenarioName())
                                + createColumnAndAssignColor(scenario.getStatus())
                                + createWrappingColumn(scenario.getShortenedErrorMessage(errorMessageCharLimit))
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForSuiteSummary(Report report) {
        return createRow(
                createColumn(report.getSuiteName())
                        + createColumn("" + report.getTotal())
                        + createColumn("" + report.getPassed())
                        + createColumn("" + report.getFailed())
                        + createColumn("" + report.getBroken())
                        + createColumn("" + report.getSkipped())
                        + createColumn("" + getTime(report.getTotalTime())));
    }

    private String getTableBodyForSuiteSummaryJUnit(Report report) {
        return createRow(
                 createColumn("" + report.getTotal())
                        + createColumn("" + report.getPassed())
                        + createColumn("" + report.getFailed())
                        + createColumn("" + report.getSkipped())
                        + createColumn("" + getTime(report.getTotalTime())));
    }

    private String getHead() {
        return "<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,"
                + "Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} "
                + "th, td { padding: 5px; text-align: left;}"
                + "table#t01 tr.alt td {background-color: #E0ECF8;} "
                + "table#t01 th{background-color: #0B2161;color: white;}"
                + "table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color: #ff2222;color: white;}"
                + "table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}"
                + "table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule: exactly;line-height:100%;}"
                + "table#t02 th{background-color: #DDFADA;color: black;text-align:center;},"
                + "p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style>"
                + "<style>h4{font-family: Trebuchet MS,Arial, Helvetica, sans-serif;text-align: left;}</style>"
                + "</head>";
    }

    private String createTable(String tableValue, String id) {
        return "<table id=" + id + ">" + tableValue + "</table>";
    }

    private String createRow(String rowValue) {
        return "<tr>" + rowValue + "</tr>";
    }

    private String createHeaderColumn(String columnName) {
        return "<th>" + columnName + "</th>";
    }

    private String createColumn(String columnValue) {
        return "<td>" + columnValue + "</td>";
    }

    private String createWrappingColumn(String columnValue) {
        return "<td style='width:150px; max-width:150px'>" + columnValue + "</td>";
    }

    private String createHeader(String header) {
        return "<h4>" + header + "</h4>";
    }

    private String createColumnAndAssignColor(String columnValue) {
        String finalString = "<td><font color=\"{Color}\">{ColumnValue}</font></td>";
        columnValue = columnValue.toUpperCase();
        if (columnValue.contains("PASSED")) {
            finalString = finalString.replace("{Color}", "green").replace(
                    "{ColumnValue}", columnValue);
        } else if (columnValue.contains("FAILED")) {
            finalString = finalString.replace("{Color}", "red").replace(
                    "{ColumnValue}", columnValue);
        } else if (columnValue.contains("BROKEN")) {
            finalString = finalString.replace("{Color}", "#B7950B").replace(
                    "{ColumnValue}", columnValue);
        } else {
            finalString = finalString.replace("{Color}", "black").replace(
                    "{ColumnValue}", columnValue);
        }

        return finalString;
    }

    private String getJobInformation() {
        String browser = aeonConfiguration.getString("aeon.browser", "");
        String environmentURL = aeonConfiguration.getString("aeon.environment", "");

        return "<td><p>Browser: " + browser + "</p>" +
                "<p>Environment URL: " + environmentURL + "</p>";
    }

    private String getTime(long time) {
        int seconds = (int) (time / 1000);
        if (seconds >= 60) {
            int minutes = seconds / 60;
            if (minutes >= 60) {
                int hours = minutes / 60;
                minutes = minutes % 60;
                return hours + " hours" + minutes + " minutes";
            }
            seconds = seconds % 60;
            return minutes + " minutes " + seconds + " seconds";
        } else {
            return seconds + " seconds";
        }
    }

    private void uploadToArtifactory(String filePath) {
        String artifactoryUrl = "url";
        String artifactoryPath = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PATH, "");
        String username = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        String password = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PASSWORD, "");

        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);

        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        HttpPut put = new HttpPut(artifactoryUrl + artifactoryPath);

        put.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("password", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        try {
            put.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            log.warn("Unsupported encoding in artifactory url parameters");
        }

        StringBuffer result;
        try {
            HttpResponse response = client.execute(put);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            log.error("Could not send report file to artifactory");
        }


    }
}
