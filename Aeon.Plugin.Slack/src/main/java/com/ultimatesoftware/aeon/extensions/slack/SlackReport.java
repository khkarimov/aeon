package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Prepares notifications and reports for sending them to Slack.
 */
class SlackReport {

    private String slackChannel1;
    private String slackChannel2;
    private boolean reportHasFailures = false;

    private ImageReport imageReport;
    private SlackBotService slackBotService;

    static Logger log = LoggerFactory.getLogger(SlackReport.class);

    /**
     * Creates a new Slack report.
     *
     * @param imageReport     The image report to send.
     * @param slackBotService The slack bot service to use.
     * @param configuration   The Slack plugin configuration object.
     */
    SlackReport(
            ImageReport imageReport,
            SlackBotService slackBotService,
            IConfiguration configuration) {
        this.imageReport = imageReport;
        this.slackBotService = slackBotService;

        this.slackChannel1 = configuration.getString(SlackConfiguration.Keys.CHANNEL_1, "");
        this.slackChannel2 = configuration.getString(SlackConfiguration.Keys.CHANNEL_2, "");
    }

    /**
     * Creates the Slack reports and sends them to the configured channels.
     *
     * @param report The report details.
     */
    void sendImageReportToSlack(Report report) {
        if (!slackChannelsAreSet()) {
            log.info("No Slack channel is set up.");
            return;
        }

        this.reportHasFailures = report.getCounts().getFailed() > 0;

        File imageReportFile = getImageReport(report);

        postReportToChannel1(imageReportFile);

        if (this.reportHasFailures) {
            postReportToChannel2(imageReportFile);

            if (report.getURL() != null && !report.getURL().isEmpty()) {
                postMessageToSlack("Test Report URL: " + report.getURL());
            }
        }
    }

    void postMessageToSlack(String message) {
        if (!this.slackChannel1.isEmpty()) {
            this.slackBotService.publishNotificationToSlack(this.slackChannel1, message);
        }

        if (!this.slackChannel2.isEmpty() && this.reportHasFailures) {
            this.slackBotService.publishNotificationToSlack(this.slackChannel2, message);
        }
    }

    private boolean slackChannelsAreSet() {
        return !(slackChannel1.isEmpty() && slackChannel2.isEmpty());
    }

    private String getImageReportTitle(Report report) {
        String reportDate = "";
        if (!report.getSequence().isEmpty()) {
            TestCase testCase = report.getSequence().get(0);
            reportDate = testCase.getStarted();
        }
        return "Automation Report - " + reportDate.replace(":", "-");
    }

    private File getImageReport(Report report) {
        String title = getImageReportTitle(report);

        String html = this.imageReport.buildReportHtml(report);
        return this.imageReport.htmlToPngFile(html, title);
    }

    private void postReportToChannel1(File imageReport) {
        if (!slackChannel1.isEmpty()) {
            this.slackBotService.uploadReportToSlack(imageReport, slackChannel1);
        }
    }

    private void postReportToChannel2(File imageReport) {
        if (!slackChannel2.isEmpty()) {
            String message = "<!here> - There are test failures. Please see attached report below.";

            this.slackBotService.publishNotificationToSlack(slackChannel2, message);
            this.slackBotService.uploadReportToSlack(imageReport, slackChannel2);
        }
    }
}
