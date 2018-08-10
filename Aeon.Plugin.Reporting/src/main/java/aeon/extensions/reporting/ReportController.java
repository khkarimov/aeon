package aeon.extensions.reporting;

import aeon.extensions.reporting.reports.HtmlReport;
import aeon.extensions.reporting.reports.SlackReport;
import aeon.extensions.reporting.services.ArtifactoryService;
import aeon.extensions.reporting.services.RnrService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportController {

    private static Logger log = LogManager.getLogger(ReportController.class);
    private static String rnrUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");

    static String writeReportsAndUpload(ReportDetails reportDetails) {
        HtmlReport htmlReport = new HtmlReport(reportDetails);

        String htmlReportUrl = uploadHtmlReport(htmlReport);

        logHtmlReportUrl(htmlReportUrl);

        String correlationId = reportDetails.getCorrelationId();
        String finalRnrUrl = uploadReportToRnr(htmlReport, htmlReportUrl, correlationId);

        SlackReport slackReport = new SlackReport(reportDetails);
        slackReport.sendImageReportToSlack(htmlReportUrl, finalRnrUrl);

        return htmlReportUrl;
    }

    private static String uploadHtmlReport(HtmlReport htmlReport) {
        String htmlReportFileName = htmlReport.createAngularReportFile();
        String htmlReportUrl = ArtifactoryService.uploadToArtifactory(htmlReportFileName);
        return htmlReportUrl;
    }

    private static String uploadReportToRnr(HtmlReport htmlReport, String htmlReportUrl, String correlationId) {
        if (rnrUrl == null) {
            return "RnR report not uploaded.";
        }

        String rnrReportFileName = htmlReport.createJsonReportFile();
        String rnrReportUrl = RnrService.uploadToRnr(rnrReportFileName, htmlReportUrl, correlationId);
        log.info("RnR URL: " + rnrReportUrl);
        return rnrReportUrl;
    }

    private static void logHtmlReportUrl(String htmlReportUrl) {
        if (htmlReportUrl != null) {
            log.info("Test Report URL: " + htmlReportUrl);
        }
    }
}
