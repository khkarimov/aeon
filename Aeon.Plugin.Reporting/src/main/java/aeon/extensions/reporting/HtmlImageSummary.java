package aeon.extensions.reporting;

import aeon.core.common.interfaces.IConfiguration;

import java.io.File;
import java.util.Queue;

public class HtmlImageSummary {

    private ReportDetails reportDetails;
    private boolean displayClassName;
    private int errorMessageCharLimit;
    private String browser;
    private String environmentUrl;

    public HtmlImageSummary(IConfiguration aeonConfiguration, IConfiguration pluginConfiguration, ReportDetails reportDetails) {
        this.browser = aeonConfiguration.getString("aeon.browser", "");
        this.environmentUrl = aeonConfiguration.getString("aeon.environment", "");

        this.displayClassName = pluginConfiguration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.errorMessageCharLimit = (int) pluginConfiguration.getDouble(ReportingConfiguration.Keys.ERROR_MESSAGE_CHARACTER_LIMIT, 300);

        this.reportDetails = reportDetails;
    }

    public File constructSummaryImageFile(String title) {
        String htmlBody = "";
        htmlBody = htmlBody + this.getHead();

        String jobInformationHeader = this.getJobInformation(browser, environmentUrl);
        htmlBody = htmlBody + this.createTable(new String[]{"Test Configuration"}, jobInformationHeader, "t02");
        htmlBody = htmlBody + getSuiteSummarySection();
        htmlBody = htmlBody + getBrokenTableSection();
        htmlBody = htmlBody + getPassTableSection();

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
                        + createColumn("" + Utils.getTime(reportDetails.getTotalTime())));
    }

    private String getTableBodyForSuiteSummaryJUnit() {
        return createRow(
                createColumn("" + reportDetails.getTotalNumberOfTests())
                        + createColumn("" + reportDetails.getNumberOfPassedTests())
                        + createColumn("" + reportDetails.getNumberOfFailedTests())
                        + createColumn("" + reportDetails.getNumberOfSkippedTests())
                        + createColumn("" + Utils.getTime(reportDetails.getTotalTime())));
    }

    private String getSuiteSummarySection() {
        if (reportDetails.getSuiteName() == null) {
            // Suite Summary for JUnit so no Suite column
            return createHeader("Overall Summary") +
                    createTable(new String[]{"Total Tests", "Passed", "Failed",
                            "Skipped", "Total Time"}, getTableBodyForSuiteSummaryJUnit(), "t01");
        } else {
            // Suite Summary for TestNG
            return createHeader("Overall Summary") +
                    createTable(new String[]{"Suite Name", "Total Tests", "Passed", "Failed",
                            "Broken", "Skipped", "Total Time"}, getTableBodyForSuiteSummary(), "t01");
        }
    }

    private String getBrokenTableSection() {
        if (reportDetails.getNumberOfFailedTests() > 0 || reportDetails.getNumberOfSkippedTests() > 0) {
            return createHeader("Failed List")
                    + createTable(getFailureHeaders(),
                    getTableBodyForFailedList(reportDetails.getScenarios(), "FAILED")
                            + getTableBodyForFailedList(reportDetails.getScenarios(), "BROKEN")
                            + getTableBodyForFailedList(reportDetails.getScenarios(), "SKIPPED"), "t03");
        } else {
            return "";
        }
    }

    private String getPassTableSection() {
        if (reportDetails.getNumberOfPassedTests() > 0) {
            return createHeader("Pass List")
                    + createTable(getSuccessHeaders(),
                    getTableBodyForPassList(reportDetails.getScenarios()), "t04") + "<br>";
        } else {
            return "";
        }
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

    private String getJobInformation(String browser, String environmentUrl) {

        return "<td><p>Browser: " + browser + "</p>" +
                "<p>Environment URL: " + environmentUrl + "</p>";
    }

    private String[] getSuccessHeaders() {
        if (displayClassName) {
            return new String[]{"Class Name", "Test Name", "Status"};
        } else {
            return new String[]{"Test Name", "Status"};
        }
    }

    private String[] getFailureHeaders() {
        if (displayClassName) {
            return new String[]{"Class Name", "Test Name", "Status", "Error Message"};
        } else {
            return new String[]{"Test Name", "Status", "Error Message"};
        }
    }
}
