package aeon.extensions.reporting;

import aeon.core.common.helpers.StringUtils;
import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.reportmodel.FailedExpectation;
import aeon.extensions.reporting.reportmodel.Result;
import aeon.extensions.reporting.reportmodel.ResultReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.http.HttpHeaders.USER_AGENT;

class ReportSummary {

    private IConfiguration aeonConfiguration;
    private IConfiguration pluginConfiguration;
    private SlackBot slackBot;
    private ReportDetails reportDetails;
    private boolean displayClassName;
    private int errorMessageCharLimit;

    private static Logger log = LogManager.getLogger(ReportSummary.class);
    private String rnrUrl;

    ReportSummary(IConfiguration pluginConfiguration, IConfiguration aeonConfiguration, ReportDetails reportDetails) {
        this.aeonConfiguration = aeonConfiguration;
        this.pluginConfiguration = pluginConfiguration;
        this.slackBot = new SlackBot(pluginConfiguration);
        this.displayClassName = pluginConfiguration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.errorMessageCharLimit = (int) pluginConfiguration.getDouble(ReportingConfiguration.Keys.ERROR_MESSAGE_CHARACTER_LIMIT, 300);

        this.reportDetails = reportDetails;
    }

    String createReportFile() {
        ResultReport resultReport = constructReportFromDetails();
        String rnrJsonReportNoEscape = toJsonString(resultReport);

        ResultReport escapedResultReport = escapeMessagesAndLogsInReport(resultReport);
        String htmlJsonReportEscaped = toJsonString(escapedResultReport);

        String reportTemplate = addJsonToHtmlTemplate(htmlJsonReportEscaped);

        String fileName = pluginConfiguration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "")
            + "/report-" + reportDetails.getCorrelationId() + ".html";

        String reportUrl = writeReportFileAndUploadToArtifactory(reportTemplate, fileName);

        String rnrUrl = pluginConfiguration.getString(ReportingConfiguration.Keys.RNR_URL, "");
        if (rnrUrl.isEmpty()) {
            return reportUrl;
        }

        writeJsonResultFile(fileName, rnrUrl, reportUrl, rnrJsonReportNoEscape);

        return reportUrl;
    }

    void sendSummaryReport(String reportUrl) {

        String reportDate = "";
        ScenarioDetails scenarioDetails = reportDetails.getScenarios().peek();
        if (scenarioDetails != null) {
            reportDate = ReportingPlugin.reportDateFormat.format(scenarioDetails.getStartTime());
        }
        String title = "Automation Report - " + reportDate.replace(":", "-");

        String slackChannel1 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
        String slackChannel2 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
        if (StringUtils.isBlank(slackChannel1) && StringUtils.isBlank(slackChannel2)) {
            log.info("No Slack channel is set up.");

            return;
        }

        List<String> messages = new ArrayList<>();

        if (reportUrl != null) {
            messages.add("Test Report URL: " + reportUrl);
        }

        if (this.rnrUrl != null) {
            messages.add("RnR URL: " + rnrUrl);
        }

        if (StringUtils.isNotBlank(slackChannel1)) {
            slackBot.uploadReportToSlack(this.summaryReport(title), slackChannel1);
            Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");

            if (!messages.isEmpty()) {
                slackBot.publishNotificationToSlack(slackChannel1, String.join("\n\n", messages));
            }
        }

        if (StringUtils.isNotBlank(slackChannel2)) {
            Boolean failed = false;
            if (reportDetails.getNumberOfFailedTests() > 0) {
                failed = true;
            }

            if (failed) {

                messages.add(0, "<!here> - There are test failures. Please see attached report below.");

                slackBot.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
                slackBot.uploadReportToSlack(this.summaryReport(title), slackChannel2);
                Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");
            } else {
                String startTime = new Date(reportDetails.getStartTime()).toString();
                String url = aeonConfiguration.getString("aeon.environment", "");
                messages.add(0, "Tests Passed for URL: " + url + " started at " + startTime);

                slackBot.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
            }
        }
    }

    private File summaryReport(String title) {
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
        if (reportDetails.getSuiteName() == null) {
            //Suite Summary for JUnit so no Suite column
            htmlBody = htmlBody + createHeader("Overall Summary") +
                    createTable(new String[]{"Total Tests", "Passed", "Failed",
                            "Skipped", "Total Time"}, getTableBodyForSuiteSummaryJUnit(), "t01");
        } else {
            //Suite Summary for TestNG
            htmlBody = htmlBody + createHeader("Overall Summary") +
                    createTable(new String[]{"Suite Name", "Total Tests", "Passed", "Failed",
                            "Broken", "Skipped", "Total Time"}, getTableBodyForSuiteSummary(), "t01");
        }
        //Broken Table
        if (reportDetails.getNumberOfFailedTests() > 0 || reportDetails.getNumberOfSkippedTests() > 0) {
            htmlBody = htmlBody + createHeader("Failed List")
                    + createTable(failureHeaders,
                    getTableBodyForFailedList(reportDetails.getScenarios(), "FAILED")
                            + getTableBodyForFailedList(reportDetails.getScenarios(), "BROKEN")
                            + getTableBodyForFailedList(reportDetails.getScenarios(), "SKIPPED"), "t03");
        }

        //Pass List
        if (reportDetails.getNumberOfPassedTests() > 0) {
            htmlBody = htmlBody + createHeader("Pass List")
                    + createTable(successHeaders,
                    getTableBodyForPassList(reportDetails.getScenarios()), "t04") + "<br>";
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

    private String getTableBodyForPassList(Queue<ScenarioDetails> scenarios) {
        StringBuilder finalBody = new StringBuilder();

        for (ScenarioDetails scenario : scenarios) {
            if (scenario.getStatus().equalsIgnoreCase("PASSED")) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(scenario.getClassName());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(scenario.getTestName())
                                + createColumnAndAssignColor(scenario.getStatus())
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForFailedList(Queue<ScenarioDetails> scenarios, String status) {
        StringBuilder finalBody = new StringBuilder();

        for (ScenarioDetails scenario : scenarios) {
            if (scenario.getStatus().equalsIgnoreCase(status)) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(scenario.getClassName());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(scenario.getTestName())
                                + createColumnAndAssignColor(scenario.getStatus())
                                + createWrappingColumn(scenario.getShortenedErrorMessage(errorMessageCharLimit))
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForSuiteSummary() {
        return createRow(
                createColumn(reportDetails.getSuiteName())
                        + createColumn("" + reportDetails.getTotalNumberOfTests())
                        + createColumn("" + reportDetails.getNumberOfPassedTests())
                        + createColumn("" + reportDetails.getNumberOfFailedTests())
                        + createColumn("" + 0)
                        + createColumn("" + reportDetails.getNumberOfSkippedTests())
                        + createColumn("" + getTime(reportDetails.getTotalTime())));
    }

    private String getTableBodyForSuiteSummaryJUnit() {
        return createRow(
                 createColumn("" + reportDetails.getTotalNumberOfTests())
                        + createColumn("" + reportDetails.getNumberOfPassedTests())
                        + createColumn("" + reportDetails.getNumberOfFailedTests())
                        + createColumn("" + reportDetails.getNumberOfSkippedTests())
                        + createColumn("" + getTime(reportDetails.getTotalTime())));
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

    private String escapeIllegalJSONCharacters(String input) {

        if (input == null) {
            return null;
        }

        return input.replace("&", "\\u0026")
                .replace("[", "\\u005B")
                .replace("]", "\\u005D")
                .replace("'", "\\u0027")
                .replace("\"", "\\u0022")
                .replace("{", "\\u007B")
                .replace("}", "\\u007D")
                .replace("|", "\\u007C")
                .replace(",", "\\u002C")
                .replace("<", "\\u003C")
                .replace(">", "\\u003E")
                .replace(".", "\\u002E");
    }

    private ResultReport escapeMessagesAndLogsInReport(ResultReport inputReport) {
        for (Result result : inputReport.sequence) {
            for (FailedExpectation failedExpectation : result.failedExpectations) {
                failedExpectation.message = escapeIllegalJSONCharacters(failedExpectation.message);
                failedExpectation.stack = escapeIllegalJSONCharacters(failedExpectation.stack);
            }

            List<Pair<String, List<String>>> replaceSteps = new ArrayList<>();
            for (Pair<String, List<String>> highLevelStep : result.steps) {
                String highLevelStepKey = escapeIllegalJSONCharacters(highLevelStep.getKey());
                List<String> stepValues = new ArrayList<>();

                for (String value : highLevelStep.getValue()) {
                    stepValues.add(escapeIllegalJSONCharacters(value));
                }

                replaceSteps.add(new Pair<>(highLevelStepKey, stepValues));
            }
            result.steps = replaceSteps;
        }
        return inputReport;
    }

    private ResultReport constructReportFromDetails() {
        ResultReport resultReport = new ResultReport();
        resultReport.counts.passed = reportDetails.getNumberOfPassedTests();
        resultReport.counts.failed = reportDetails.getNumberOfFailedTests();
        resultReport.counts.disabled = reportDetails.getNumberOfSkippedTests();
        resultReport.timer.duration = reportDetails.getTotalTime();
        for (ScenarioDetails scenario: reportDetails.getScenarios()) {
            Result result = new Result();
            result.description = scenario.getTestName();
            result.prefix = scenario.getClassName() + " ";
            if (reportDetails.getSuiteName() != null) {
                result.prefix  = reportDetails.getSuiteName() + " " + scenario.getClassName() + " ";
            }

            result.started = ReportingPlugin.uploadDateFormat.format(new Date(scenario.getStartTime()));
            result.stopped = ReportingPlugin.uploadDateFormat.format(new Date(scenario.getEndTime()));
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
                        stream.close();
                    } catch (IOException e) {
                        log.warn("Could not write screenshot", e);
                    }
                }
            }

            result.videoUrl = scenario.getVideoUrl();
            result.steps = scenario.getSteps();

            resultReport.sequence.add(result);
        }
        return resultReport;
    }

    private String toJsonString(Object inputObject) {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(inputObject);

        } catch (JsonProcessingException e) {
            log.error("Could not write JSON results", e);

            return null;
        }
        return json;
    }

    private String addJsonToHtmlTemplate(String jsonReport) {
        String reportTemplate;
        try (InputStream scriptReader = ReportingPlugin.class.getResourceAsStream("/report.tmpl.html")) {
            reportTemplate =  new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            log.error("File not found on path");

            return null;
        } catch (IOException e) {
            log.error("Problem reading from file");

            return null;
        }

        jsonReport = jsonReport.replace("\\n", "\\\\n")
                .replace("\\t", "\\\\t")
                .replace("\\\"", "\\\\\\\"")
                .replace("'", "\\\\\\\"");
        String script = "<script>RESULTS.push(JSON.parse('" + jsonReport + "'));</script>";
        reportTemplate = reportTemplate.replace("<!-- inject::scripts -->", script);
        return reportTemplate;
    }

    private String writeReportFileAndUploadToArtifactory(String reportTemplate, String fileName) {
        String reportUrl = null;
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(reportTemplate);
            out.close();

            reportUrl = uploadToArtifactory(pluginConfiguration, fileName);

            if (reportUrl != null) {
                log.info("Test Report URL: " + reportUrl);
            }
        } catch (FileNotFoundException e) {
            log.error("File not found on path.");
        }

        return reportUrl;
    }

    private void writeJsonResultFile(String fileName, String rnrUrl, String reportUrl, String jsonReport) {
        String jsonFileName = fileName.replace(".html", ".json");
        try (PrintWriter out = new PrintWriter(jsonFileName)) {
            out.println(jsonReport);
            out.close();

            this.rnrUrl = uploadToRnR(rnrUrl, jsonFileName, reportUrl);
        } catch (FileNotFoundException e) {
            log.error("File not found on path.");
        }
    }

    static String uploadToArtifactory(IConfiguration pluginConfiguration, String filePathName) {
        String artifactoryUrl = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_URL, "");
        String artifactoryPath = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PATH, "");
        String username = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        String password = pluginConfiguration.getString(ReportingConfiguration.Keys.ARTIFACTORY_PASSWORD, "");

        if (artifactoryUrl.isEmpty() || artifactoryPath.isEmpty() || username.isEmpty() || password.isEmpty()) {
            log.info("Not all artifactory properties set, cancelling file upload.");
            return null;
        }

        String fileName = filePathName.substring(filePathName.lastIndexOf("/")+1);
        String fullRequestUrl = artifactoryUrl + "/" + artifactoryPath + "/" + fileName;

        // Set basic auth information
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        provider.setCredentials(AuthScope.ANY, credentials);

        // Build file entity using InputStream
        File file = new File(filePathName);
        InputStreamEntity fileEntity;
        try {
            InputStream inputStream = new FileInputStream(filePathName);
            fileEntity = new InputStreamEntity(inputStream, file.length());
        } catch (FileNotFoundException e) {
            log.error(String.format("Could not find file %s to upload to artifactory", filePathName));
            return null;
        }

        // Build HttpClient and build put request
        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        HttpPut put = new HttpPut(fullRequestUrl);

        put.setHeader("User-Agent", USER_AGENT);
        put.setHeader("Content-type", "text/html");
        put.setEntity(fileEntity);

        // Execute request and receive response
        try {
            HttpResponse response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_CREATED) {
                log.error(String.format("Did not receive successful status code for artifactory upload. Received: %d", statusCode));
            }
        } catch (IOException e) {
            log.error(String.format("Could not upload report file '%s' to artifactory: %s", fullRequestUrl, e.getMessage()), e);

            return null;
        }

        return fullRequestUrl;
    }

    private String uploadToRnR(String rnrUrl, String filePathName, String reportUrl) {
        String product = pluginConfiguration.getString(ReportingConfiguration.Keys.PRODUCT, "");
        String team = pluginConfiguration.getString(ReportingConfiguration.Keys.TEAM, "");
        String type = pluginConfiguration.getString(ReportingConfiguration.Keys.TYPE, "");
        String branch = pluginConfiguration.getString(ReportingConfiguration.Keys.BRANCH, "");

        if (product.isEmpty() || team.isEmpty() || type.isEmpty() || branch.isEmpty()) {
            log.trace("Not all RnR properties are set, cancelling upload to RnR.");
            return null;
        }

        String fullRequestUrl = rnrUrl + "/testsuite";

        // Build file entity using InputStream
        File file = new File(filePathName);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("namespace", team);
        builder.addTextBody("project", product);
        builder.addTextBody("type", type);
        builder.addTextBody("branch", branch);
        builder.addTextBody("correlationId", reportDetails.getCorrelationId());
        builder.addBinaryBody("file", file,
                ContentType.APPLICATION_OCTET_STREAM, "results.js");

        Map<String, String> rnrMetaMap = new HashMap<>();
        rnrMetaMap.put("screenshots", reportUrl);
        rnrMetaMap.put("app", aeonConfiguration.getString("aeon.environment", ""));
        rnrMetaMap.put("browser", aeonConfiguration.getString("aeon.browser", "Chrome"));

        try {
            String rnrMeta = new ObjectMapper().writeValueAsString(rnrMetaMap);
            File rnrMetaFile = File.createTempFile("rnr-", "-meta.rnr");
            rnrMetaFile.deleteOnExit();
            PrintWriter out = new PrintWriter(rnrMetaFile);
            out.println(rnrMeta);
            out.close();
            builder.addBinaryBody("metaFile", rnrMetaFile,
                    ContentType.APPLICATION_OCTET_STREAM, "meta.rnr");
        } catch (JsonProcessingException e) {
            log.error("Could not write JSON results.", e);
        } catch (IOException e) {
            log.error("Could not write meta RnR file.", e);
        }

        HttpEntity multipart = builder.build();

        // Build HttpClient and build post request
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(fullRequestUrl);
        post.setEntity(multipart);

        // Execute request and receive response
        try {
            HttpResponse response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_ACCEPTED) {
                log.error(String.format("Did not receive successful status code for RnR upload. Received: %d.", statusCode));
            }
        } catch (IOException e) {
            log.error(String.format("Could not upload report to RnR (%s): %s.", fullRequestUrl, e.getMessage()), e);
        }

        String rnrResultUrl = rnrUrl + "/" + reportDetails.getCorrelationId();

        log.info("RnR URL: " + rnrResultUrl);

        return rnrResultUrl;
    }
}
