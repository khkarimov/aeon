package aeon.extensions.reporting.reports;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportController;
import aeon.extensions.reporting.ReportingConfiguration;
import aeon.extensions.reporting.ReportingPlugin;
import aeon.extensions.reporting.models.ReportDetails;
import aeon.extensions.reporting.models.ScenarioDetails;
import aeon.extensions.reporting.resultreportmodel.FailedExpectation;
import aeon.extensions.reporting.resultreportmodel.ResultReport;
import aeon.extensions.reporting.resultreportmodel.TestCaseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Creates an HTML report from the test results.
 */
public class HtmlReport {

    private ReportDetails reportDetails = null;
    private String reportsDirectory;

    private String jsonReport;

    private final SimpleDateFormat uploadDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static Logger log = LoggerFactory.getLogger(HtmlReport.class);

    /**
     * Sets the Reporting plugin configuration.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    public void setConfiguration(IConfiguration configuration) {
        this.reportsDirectory = configuration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");
    }

    /**
     * Constructs the report from the test results and transform it into JSON.
     *
     * @param reportDetails The details of the test results.
     */
    public void prepareReport(ReportDetails reportDetails) {
        this.reportDetails = reportDetails;

        ResultReport resultReport = constructReportFromDetails();
        jsonReport = toJsonString(resultReport);
    }

    /**
     * Creates the HTML report file.
     *
     * @return The name and path of the created HTML report file.
     */
    public String createAngularReportFile() {
        String reportTemplate = addJsonToHtmlTemplate(jsonReport);

        String angularReportFileName = reportsDirectory
                + "/report-" + reportDetails.getCorrelationId() + ".html";

        writeFile(reportTemplate, angularReportFileName);

        return angularReportFileName;
    }

    /**
     * Creates the JSON report file.
     *
     * @return The name and path of the created JSON report file.
     */
    public String createJsonReportFile() {
        String jsonReportFileName = reportsDirectory
                + "/report-" + reportDetails.getCorrelationId() + ".json";
        writeFile(jsonReport, jsonReportFileName);
        return jsonReportFileName;
    }

    private ResultReport constructReportFromDetails() {
        ResultReport resultReport = new ResultReport();
        resultReport.getCounts().setPassed(reportDetails.getNumberOfPassedTests());
        resultReport.getCounts().setFailed(reportDetails.getNumberOfFailedTests());
        resultReport.getCounts().setDisabled(reportDetails.getNumberOfSkippedTests());
        resultReport.getTimer().setDuration(reportDetails.getTotalTime());
        for (ScenarioDetails scenario : reportDetails.getScenarios()) {
            TestCaseResult testCaseResult = new TestCaseResult();
            testCaseResult.setDescription(scenario.getTestName());
            testCaseResult.setPrefix(scenario.getClassName() + " ");
            if (reportDetails.getSuiteName() != null) {
                testCaseResult.setPrefix(reportDetails.getSuiteName() + " " + scenario.getClassName() + " ");
            }

            testCaseResult.setStarted(uploadDateFormat.format(new Date(scenario.getStartTime())));
            testCaseResult.setStopped(uploadDateFormat.format(new Date(scenario.getEndTime())));
            testCaseResult.setDuration(ReportController.getTime(scenario.getEndTime() - scenario.getStartTime())
                    .replace(" seconds", "s"));
            testCaseResult.setStatus(scenario.getStatus().toLowerCase());
            if (testCaseResult.getStatus().equals("skipped")) {
                testCaseResult.setStatus("disabled");
            }

            if (testCaseResult.getStatus().equals("failed")) {
                FailedExpectation failedExpectation = new FailedExpectation();
                failedExpectation.setMessage(scenario.getErrorMessage());
                failedExpectation.setStack(scenario.getStackTrace());
                testCaseResult.getFailedExpectations().add(failedExpectation);

                Image screenshot = scenario.getScreenshot();
                if (screenshot != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        ImageIO.write((BufferedImage) screenshot, "png", stream);
                        String data = DatatypeConverter.printBase64Binary(stream.toByteArray());
                        testCaseResult.setScreenshotPath("data:image/png;base64," + data);
                        stream.close();
                    } catch (IOException e) {
                        log.warn("Could not write screenshot", e);
                    }
                }
            }

            testCaseResult.setVideoUrl(scenario.getVideoUrl());
            testCaseResult.setBrowserLogs(scenario.getBrowserLogs());
            testCaseResult.setSteps(scenario.getSteps());

            resultReport.getSequence().add(testCaseResult);
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
            reportTemplate = new BufferedReader(new InputStreamReader(scriptReader)).lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            log.error("File not found on path");

            return null;
        } catch (IOException e) {
            log.error("Problem reading from file");

            return null;
        }

        String script = "<script>\n" +
                "let jsonReport = " + jsonReport + ";\n" +
                "RESULTS.push(jsonReport);\n" +
                "</script>";
        reportTemplate = reportTemplate.replace("<!-- inject::scripts -->", script);
        return reportTemplate;
    }

    private void writeFile(String contentToWrite, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(contentToWrite);
        } catch (FileNotFoundException e) {
            log.error("File not found on path.");
        }
    }
}
