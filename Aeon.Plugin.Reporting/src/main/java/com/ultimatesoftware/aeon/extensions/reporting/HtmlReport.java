package com.ultimatesoftware.aeon.extensions.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Creates an HTML report from the test results.
 */
public class HtmlReport {

    private String reportsDirectory;

    private Report report;
    private String jsonReport;

    private final SimpleDateFormat uploadDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    static Logger log = LoggerFactory.getLogger(HtmlReport.class);

    /**
     * Instantiates a new object of {@link HtmlReport}.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    HtmlReport(IConfiguration configuration) {
        this.reportsDirectory = configuration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");
    }

    /**
     * Constructs the report from the test results and transform it into JSON.
     *
     * @param report The details of the test results.
     */
    void prepareReport(Report report) {
        this.report = report;
        this.calculateResults();

        jsonReport = toJsonString(report);
    }

    private void calculateResults() {

        int numberOfPassedTests = 0;
        int numberOfFailedTests = 0;
        int numberOfSkippedTests = 0;
        for (TestCase testCase : this.report.getSequence()) {
            switch (testCase.getStatus()) {
                case "passed":
                    numberOfPassedTests++;
                    break;
                case "failed":
                    numberOfFailedTests++;
                    break;
                default:
                    numberOfSkippedTests++;
                    break;
            }
        }

        this.report.getCounts().setPassed(numberOfPassedTests);
        this.report.getCounts().setFailed(numberOfFailedTests);
        this.report.getCounts().setDisabled(numberOfSkippedTests);
        long startTime = this.report.getTimer().getStartTime();
        long endTime = this.report.getTimer().getEndTime();
        this.report.getTimer().setDuration(endTime - startTime);
        this.report.getTimer().setStarted(uploadDateFormat.format(new Date(startTime)));
        this.report.getTimer().setStopped(uploadDateFormat.format(new Date(endTime)));
    }

    /**
     * Returns the generated report.
     *
     * @return The generated report.
     */
    public Report getReport() {
        return this.report;
    }

    /**
     * Returns the generated report as a JSON string.
     *
     * @return The generated report as a JSON string.
     */
    String getJsonReport() {
        return this.jsonReport;
    }

    /**
     * Creates the HTML report file.
     *
     * @return The name and path of the created HTML report file.
     */
    String createAngularReportFile() {
        String reportTemplate = addJsonToHtmlTemplate(jsonReport);

        String angularReportFileName = reportsDirectory
                + "/report-" + report.getCorrelationId() + ".html";

        HtmlReport.writeFile(reportTemplate, angularReportFileName);

        return angularReportFileName;
    }

    /**
     * Writes a string into a file.
     *
     * @param contentToWrite The string to write into the file.
     * @param fileName       Name and path of the file to write to.
     */
    public static void writeFile(String contentToWrite, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(contentToWrite);
        } catch (FileNotFoundException e) {
            log.error("File not found on path.", e);
        }
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
            reportTemplate = new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            log.error("File not found on path", e);

            return null;
        } catch (IOException e) {
            log.error("Problem reading from file", e);

            return null;
        }

        String script = "<script>\n" +
                "let jsonReport = " + jsonReport + ";\n" +
                "RESULTS.push(jsonReport);\n" +
                "</script>";
        reportTemplate = reportTemplate.replace("<!-- inject::scripts -->", script);
        return reportTemplate;
    }
}
