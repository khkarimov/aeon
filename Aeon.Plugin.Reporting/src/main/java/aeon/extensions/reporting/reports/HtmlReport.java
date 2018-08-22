package aeon.extensions.reporting.reports;

import aeon.extensions.reporting.*;
import aeon.extensions.reporting.resultreportmodel.FailedExpectation;
import aeon.extensions.reporting.resultreportmodel.ResultReport;
import aeon.extensions.reporting.resultreportmodel.TestCaseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.stream.Collectors;

public class HtmlReport {

    private ReportDetails reportDetails;
    private String reportsDirectory= ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");;

    private ResultReport resultReport;
    private String jsonReport;

    private static Logger log = LogManager.getLogger(HtmlReport.class);

    public HtmlReport(ReportDetails reportDetails) {
        this.reportDetails = reportDetails;

        processInfo();
    }

    private void processInfo() {
        resultReport = constructReportFromDetails();
        jsonReport = toJsonString(resultReport);
    }

    public String createAngularReportFile() {
        String reportTemplate = addJsonToHtmlTemplate(jsonReport);

        String angularReportFileName = reportsDirectory
                + "/report-" + reportDetails.getCorrelationId() + ".html";

        writeFile(reportTemplate, angularReportFileName);

        return angularReportFileName;
    }

    public String createJsonReportFile() {
        String jsonReportFileName = reportsDirectory
                + "/report-" + reportDetails.getCorrelationId() + ".json";
        writeFile(jsonReport, jsonReportFileName);
        return jsonReportFileName;
    }

    private ResultReport constructReportFromDetails() {
        ResultReport resultReport = new ResultReport();
        resultReport.counts.passed = reportDetails.getNumberOfPassedTests();
        resultReport.counts.failed = reportDetails.getNumberOfFailedTests();
        resultReport.counts.disabled = reportDetails.getNumberOfSkippedTests();
        resultReport.timer.duration = reportDetails.getTotalTime();
        for (ScenarioDetails scenario: reportDetails.getScenarios()) {
            TestCaseResult testCaseResult = new TestCaseResult();
            testCaseResult.description = scenario.getTestName();
            testCaseResult.prefix = scenario.getClassName() + " ";
            if (reportDetails.getSuiteName() != null) {
                testCaseResult.prefix  = reportDetails.getSuiteName() + " " + scenario.getClassName() + " ";
            }

            testCaseResult.started = ReportingPlugin.UPLOAD_DATE_FORMAT.format(new Date(scenario.getStartTime()));
            testCaseResult.stopped = ReportingPlugin.UPLOAD_DATE_FORMAT.format(new Date(scenario.getEndTime()));
            testCaseResult.duration = Utils.getTime(scenario.getEndTime() - scenario.getStartTime()).replace(" seconds", "s");
            testCaseResult.status = scenario.getStatus().toLowerCase();
            if (testCaseResult.status.equals("skipped")) {
                testCaseResult.status = "disabled";
            }

            if (testCaseResult.status.equals("failed")) {
                FailedExpectation failedExpectation = new FailedExpectation();
                failedExpectation.message = scenario.getErrorMessage();
                failedExpectation.stack = scenario.getStackTrace();
                testCaseResult.failedExpectations.add(failedExpectation);

                Image screenshot = scenario.getScreenshot();
                if (screenshot != null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try {
                        ImageIO.write((BufferedImage) screenshot, "png", stream);
                        String data = DatatypeConverter.printBase64Binary(stream.toByteArray());
                        testCaseResult.screenshotPath = "data:image/png;base64," + data;
                        stream.close();
                    } catch (IOException e) {
                        log.warn("Could not write screenshot", e);
                    }
                }
            }

            testCaseResult.videoUrl = scenario.getVideoUrl();
            testCaseResult.browserLogs = scenario.getBrowserLogs();
            testCaseResult.steps = scenario.getSteps();

            resultReport.sequence.add(testCaseResult);
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
