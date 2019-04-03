package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingPlugin;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.EditorKit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Creates a report as an image file from the test results.
 */
class ImageReport {

    private static final String COLOR_PLACEHOLDER = "{Color}";
    private static final String COLUMN_VALUE_PLACEHOLDER = "{ColumnValue}";
    private static final String TEST_NAME = "Test Name";
    private static final String STATUS = "Status";
    private boolean displayClassName;
    private int errorMessageCharLimit;
    private String browser;
    private String environmentUrl;

    private static Logger log = LoggerFactory.getLogger(ImageReport.class);

    /**
     * Instantiates a new object of {@link ImageReport}.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    ImageReport(IConfiguration configuration) {
        this.displayClassName = configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.errorMessageCharLimit = (int) configuration.getDouble(SlackConfiguration.Keys.ERROR_MESSAGE_CHARACTER_LIMIT, 300);
        this.browser = configuration.getString("aeon.browser", "");
        this.environmentUrl = configuration.getString("aeon.environment", "");
    }

    /**
     * Creates the image report file.
     *
     * @param report The report details.
     * @return The created report HTML.
     */
    String buildReportHtml(Report report) {
        String htmlBody = "";
        htmlBody = htmlBody + this.getHead();

        String jobInformationHeader = this.getJobInformation(browser, environmentUrl);
        htmlBody = htmlBody + this.createTable(new String[]{"Test Configuration"}, jobInformationHeader, "t02");
        htmlBody = htmlBody + getSuiteSummarySection(report);
        htmlBody = htmlBody + getBrokenTableSection(report);
        htmlBody = htmlBody + getPassTableSection(report);

        return htmlBody;
    }

    /**
     * Creates the image report file.
     *
     * @param html  The report in HTML format.
     * @param title The name to use for the file.
     * @return The created image report file.
     */
    File htmlToPngFile(String html, String title) {
        log.trace("Converting HTML file to Png");
        JEditorPane jep = new JEditorPane();
        jep.setSize(800, 800);
        EditorKit kit = jep.getEditorKit();
        jep.setEditorKitForContentType("text/html; charset=UTF-8", kit);
        jep.setContentType("text/html; charset=UTF-8");
        jep.setText(html);
        Dimension prefsize = jep.getPreferredSize();
        BufferedImage image = new BufferedImage(prefsize.width, jep.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        jep.setSize(prefsize);
        jep.paint(graphics);
        File file = new File(this.getResourcesPath() + title + ".png");
        file.deleteOnExit();
        file.getParentFile().mkdirs();
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            log.error("Error saving image", e);
            return null;
        }
        return file;
    }

    private String createTable(String[] headers, String tableBody, String id) {
        StringBuilder finalHeader = new StringBuilder();
        for (String header : headers) {
            finalHeader.append(this.createHeaderColumn(header));
        }
        return this.createTable(finalHeader + tableBody, id);
    }

    private String getTableBodyForPassList(List<TestCase> testCases) {
        StringBuilder finalBody = new StringBuilder();

        for (TestCase testCase : testCases) {
            if (testCase.getStatus().equalsIgnoreCase("passed")) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(testCase.getPrefix());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(testCase.getDescription())
                                + createColumnAndAssignColor(testCase.getStatus())
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForFailedList(List<TestCase> testCases, String status) {
        StringBuilder finalBody = new StringBuilder();

        for (TestCase testCase : testCases) {
            if (testCase.getStatus().equalsIgnoreCase(status)) {
                String classColumn;
                if (displayClassName) {
                    classColumn = createColumn(testCase.getPrefix());
                } else {
                    classColumn = "";
                }

                finalBody.append(createRow(
                        classColumn
                                + createColumn(testCase.getDescription())
                                + createColumnAndAssignColor(testCase.getStatus())
                                + createWrappingColumn(testCase.getShortenedErrorMessage(errorMessageCharLimit))
                ));
            }
        }
        return finalBody.toString();
    }

    private String getTableBodyForSuiteSummary(Report report) {
        return createRow(
                createColumn(report.getName())
                        + createColumn("" + report.getCounts().getTotal())
                        + createColumn("" + report.getCounts().getPassed())
                        + createColumn("" + report.getCounts().getFailed())
                        + createColumn("" + 0)
                        + createColumn("" + report.getCounts().getDisabled())
                        + createColumn("" + ReportingPlugin.getTime(report.getTimer().getDuration())));
    }

    private String getTableBodyForSuiteSummaryJUnit(Report report) {
        return createRow(
                createColumn("" + report.getCounts().getTotal())
                        + createColumn("" + report.getCounts().getPassed())
                        + createColumn("" + report.getCounts().getFailed())
                        + createColumn("" + report.getCounts().getDisabled())
                        + createColumn("" + ReportingPlugin.getTime(report.getTimer().getDuration())));
    }

    private String getSuiteSummarySection(Report report) {
        if (report.getName() == null || report.getName().isEmpty()) {
            // Suite Summary for JUnit so no Suite column
            return createHeader("Overall Summary") +
                    createTable(new String[]{"Total Tests", "Passed", "Failed",
                            "Skipped", "Total Time"}, getTableBodyForSuiteSummaryJUnit(report), "t01");
        } else {
            // Suite Summary for TestNG
            return createHeader("Overall Summary") +
                    createTable(new String[]{"Suite Name", "Total Tests", "Passed", "Failed",
                            "Broken", "Skipped", "Total Time"}, getTableBodyForSuiteSummary(report), "t01");
        }
    }

    private String getBrokenTableSection(Report report) {
        if (report.getCounts().getFailed() > 0 || report.getCounts().getDisabled() > 0) {
            return createHeader("Failed List")
                    + createTable(getFailureHeaders(),
                    getTableBodyForFailedList(report.getSequence(), "FAILED")
                            + getTableBodyForFailedList(report.getSequence(), "BROKEN")
                            + getTableBodyForFailedList(report.getSequence(), "DISABLED"), "t03");
        } else {
            return "";
        }
    }

    private String getPassTableSection(Report report) {
        if (report.getCounts().getPassed() > 0) {
            return createHeader("Pass List")
                    + createTable(getSuccessHeaders(),
                    getTableBodyForPassList(report.getSequence()), "t04") + "<br>";
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
            finalString = finalString.replace(COLOR_PLACEHOLDER, "green").replace(
                    COLUMN_VALUE_PLACEHOLDER, columnValue);
        } else if (columnValue.contains("FAILED")) {
            finalString = finalString.replace(COLOR_PLACEHOLDER, "red").replace(
                    COLUMN_VALUE_PLACEHOLDER, columnValue);
        } else if (columnValue.contains("BROKEN")) {
            finalString = finalString.replace(COLOR_PLACEHOLDER, "#B7950B").replace(
                    COLUMN_VALUE_PLACEHOLDER, columnValue);
        } else {
            finalString = finalString.replace(COLOR_PLACEHOLDER, "black").replace(
                    COLUMN_VALUE_PLACEHOLDER, columnValue);
        }

        return finalString;
    }

    private String getJobInformation(String browser, String environmentUrl) {

        return "<td><p>Browser: " + browser + "</p>" +
                "<p>Environment URL: " + environmentUrl + "</p>";
    }

    private String[] getSuccessHeaders() {
        if (displayClassName) {
            return new String[]{"Class Name", TEST_NAME, STATUS};
        } else {
            return new String[]{TEST_NAME, STATUS};
        }
    }

    private String[] getFailureHeaders() {
        if (displayClassName) {
            return new String[]{"Class Name", TEST_NAME, STATUS, "Error Message"};
        } else {
            return new String[]{TEST_NAME, STATUS, "Error Message"};
        }
    }

    private String getResourcesPath() {
        String resourcePath;
        resourcePath = System.getProperty("user.dir") + "/src/test/resources/";

        return resourcePath;
    }
}
