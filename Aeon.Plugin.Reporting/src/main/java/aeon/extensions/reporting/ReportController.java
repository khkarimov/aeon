package aeon.extensions.reporting;

import aeon.extensions.reporting.reports.HtmlReport;
import aeon.extensions.reporting.reports.SlackReport;
import aeon.extensions.reporting.services.ArtifactoryService;
import aeon.extensions.reporting.services.RnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportController {

    private static Logger log = LoggerFactory.getLogger(ReportController.class);
    private static String rnrUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");

    static String writeReportsAndUpload(ReportDetails reportDetails) {
        HtmlReport htmlReport = new HtmlReport(reportDetails);

        String htmlReportUrl = uploadHtmlReport(htmlReport);

        if (htmlReportUrl != null) {
            log.info("Test Report URL: " + htmlReportUrl);
        }

        String correlationId = reportDetails.getCorrelationId();
        String finalRnrUrl = uploadReportToRnr(htmlReport, htmlReportUrl, correlationId);

        SlackReport slackReport = new SlackReport(reportDetails);
        slackReport.sendImageReportToSlack(htmlReportUrl, finalRnrUrl);

        return htmlReportUrl;
    }

    private static String uploadHtmlReport(HtmlReport htmlReport) {
        String htmlReportFileName = htmlReport.createAngularReportFile();

        return ArtifactoryService.uploadToArtifactory(htmlReportFileName);
    }

    private static String uploadReportToRnr(HtmlReport htmlReport, String htmlReportUrl, String correlationId) {
        if (rnrUrl.isEmpty()) {
            log.trace("RnR URL not set, cancelling upload to RnR.");
            return null;
        }

        String rnrReportFileName = htmlReport.createJsonReportFile();

        return RnrService.uploadToRnr(rnrReportFileName, htmlReportUrl, correlationId);
    }
}
