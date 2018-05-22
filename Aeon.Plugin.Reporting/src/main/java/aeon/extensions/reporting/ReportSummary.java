package aeon.extensions.reporting;

import aeon.core.common.helpers.StringUtils;
import aeon.core.common.interfaces.IConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class ReportSummary {

    private IConfiguration aeonConfiguration;
    private IConfiguration pluginConfiguration;
    private SlackBot slackBot;

    private static Logger log = LogManager.getLogger(ReportSummary.class);

    ReportSummary(IConfiguration pluginConfiguration, IConfiguration aeonConfiguration) {
        this.aeonConfiguration = aeonConfiguration;
        this.pluginConfiguration = pluginConfiguration;
        this.slackBot = new SlackBot(pluginConfiguration);
    }

    public void sendSummaryReport(Report reportBean) {

        String title = "Automation Report---ULTIProLaunch--" + reportBean.getScenarioBeans().get(0).getStartTime().replace(":", "-");

        String slackChannel1 = pluginConfiguration.getString("aeon.extensions.reporting.slack.channel.1", "#test-channel");
        String slackChannel2 = pluginConfiguration.getString("aeon.extensions.reporting.slack.channel.2", "#test-notifications");
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
                String message = "Tests Passed for URL: " + aeonConfiguration.getString("aeon.environment", "") + " at " + ReportingPlugin.ReportingTestExecutionExtension.startTime;
                slackBot.publishNotificationToSlack(slackChannel2, message);
            }
        }
    }

    private File summaryReport(Report reportBean, String title) {
        String htmlBody = "";
        htmlBody = htmlBody + this.getHead();

        //Job Info
        htmlBody = htmlBody + this.createTable(new String[]{"Test Configuration"}, this.getJobInformation(), "t02");

        //Suite Summary
        htmlBody = htmlBody + createHeader("Overall Summary") +
                createTable(new String[]{"Suite Name", "Total Tests", "Passed", "Failed",
                        "Broken", "Skipped", "Total Time"}, getTableBodyForSuiteSummary(reportBean), "t01");

        //Broken Table
        if (reportBean.isSuiteFailed() || reportBean.isSuiteBroken() || reportBean.isSuiteSkipped())
            htmlBody = htmlBody + createHeader("Failed List")
                    + createTable(new String[]{"Class Name", "Test Name", "Status", "Error Message"},
                    getTableBodyForFailedList(reportBean.getScenarioBeans(), "FAILED")
                            + getTableBodyForFailedList(reportBean.getScenarioBeans(), "BROKEN")
                            + getTableBodyForFailedList(reportBean.getScenarioBeans(), "SKIPPED"), "t03");

        //Pass List
        if (reportBean.isSuitePassed())
            htmlBody = htmlBody + createHeader("Pass List")
                    + createTable(new String[]{"Class Name", "Test Name", "Status"},
                    getTableBodyForPassList(reportBean.getScenarios()), "t04") + "<br>";

        htmlBody = htmlBody + this.createEmptySpaceInReport();
        htmlBody = htmlBody + this.createEmptySpaceInReport();

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
                finalBody.append(createRow(
                        createColumn(scenario.getModuleName())
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
                finalBody.append(createRow(
                        createColumn(scenario.getModuleName())
                                + createColumn(scenario.getScenarioName())
                                + createColumnAndAssignColor(scenario.getStatus())
                                + createColumn(scenario.getErrorMessage())
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
                        + createColumn("" + report.getTotalTime()));
    }

    private String getHead() {
        return "<head><style> @page { size: A4 landscape;} table { width:85%;font-size:x-small}, th, td {font-family: Trebuchet MS,"
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

    private String createHeader(String header) {
        return "<h4>" + header + "</h4>";
    }

    private String createEmptySpaceInReport() {
        return "<h2 style=\"color:#279723\">" + " " + "</h4>";
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
        String url = aeonConfiguration.getString("aeon.environment", "");
        String teamName = pluginConfiguration.getString("aeon.extension.reporting.name", "");

        return "<td><p>Project : " + teamName + "</p>" +
                "<p>Browser : " + browser + "</p>" +
                "<p>HomePageURL : " + url + "</p>";
    }
}
