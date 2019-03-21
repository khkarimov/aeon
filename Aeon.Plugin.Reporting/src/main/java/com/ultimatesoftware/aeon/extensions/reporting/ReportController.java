package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.ReportDetails;
import com.ultimatesoftware.aeon.extensions.reporting.reports.HtmlReport;
import com.ultimatesoftware.aeon.extensions.reporting.reports.SlackReport;
import com.ultimatesoftware.aeon.extensions.reporting.services.ArtifactoryService;
import com.ultimatesoftware.aeon.extensions.reporting.services.RnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls creation of the different reports.
 */
public class ReportController {

    private static Logger log = LoggerFactory.getLogger(ReportController.class);
    private String rnrUrl;
    private HtmlReport htmlReport;
    private SlackReport slackReport;
    private ArtifactoryService artifactoryService;
    private RnrService rnrService;

    ReportController(
            HtmlReport htmlReport,
            SlackReport slackReport,
            ArtifactoryService artifactoryService,
            RnrService rnrService
    ) {

        this.htmlReport = htmlReport;
        this.slackReport = slackReport;
        this.artifactoryService = artifactoryService;
        this.rnrService = rnrService;
    }

    /**
     * Sets the configuration objects on all dependent classes.
     *
     * @param configuration     The Reporting plugin configuration object.
     * @param aeonConfiguration The Aeon configuration object.
     */
    void setConfiguration(IConfiguration configuration, IConfiguration aeonConfiguration) {
        this.htmlReport.setConfiguration(configuration);
        this.slackReport.setConfiguration(configuration, aeonConfiguration);
        this.artifactoryService.setConfiguration(configuration);
        this.rnrService.setConfiguration(configuration, aeonConfiguration);

        this.rnrUrl = configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");
    }

    void writeReportsAndUpload(ReportDetails reportDetails) {

        // HTML Report
        this.htmlReport.prepareReport(reportDetails);
        String htmlReportUrl = this.uploadHtmlReport(this.htmlReport);

        if (htmlReportUrl != null) {
            log.info("Test Report URL: {}", htmlReportUrl);
        }

        // RnR Report
        String correlationId = reportDetails.getCorrelationId();
        String finalRnrUrl = this.uploadReportToRnr(htmlReport, htmlReportUrl, correlationId);

        if (finalRnrUrl != null) {
            log.info("RnR URL: {}", finalRnrUrl);
        }

        // Slack Report
        this.slackReport.setReportDetails(reportDetails);
        this.slackReport.sendImageReportToSlack(htmlReportUrl, finalRnrUrl);
    }

    private String uploadHtmlReport(HtmlReport htmlReport) {
        String htmlReportFileName = htmlReport.createAngularReportFile();

        return this.artifactoryService.uploadToArtifactory(htmlReportFileName);
    }

    private String uploadReportToRnr(HtmlReport htmlReport, String htmlReportUrl, String correlationId) {
        if (this.rnrUrl.isEmpty()) {
            log.trace("RnR URL not set, cancelling upload to RnR.");
            return null;
        }

        String rnrReportFileName = htmlReport.createJsonReportFile();

        return this.rnrService.uploadToRnr(rnrReportFileName, htmlReportUrl, correlationId);
    }

    /**
     * Formats a time duration in a readable way.
     *
     * @param time The duration to format.
     * @return The formatted string.
     */
    public static String getTime(long time) {
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
}
