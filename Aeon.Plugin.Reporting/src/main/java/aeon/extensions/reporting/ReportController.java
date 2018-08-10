package aeon.extensions.reporting;

import aeon.core.common.helpers.StringUtils;
import aeon.extensions.reporting.services.ArtifactoryService;
import aeon.extensions.reporting.services.RnrService;
import aeon.extensions.reporting.services.SlackBotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.List;

public class ReportController {

    private static Logger log = LogManager.getLogger(ReportController.class);
    private static String rnrUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.RNR_URL, "");

    static String writeReportsAndUpload(ReportDetails reportDetails) {
        HtmlReport htmlReport = new HtmlReport(reportDetails);
        String angularReportFileName = htmlReport.createAngularReportFile();
        String angularReportUrl = ArtifactoryService.uploadToArtifactory(angularReportFileName);

        if (angularReportUrl != null) {
            log.info("Test Report URL: " + angularReportUrl);
        }
        if (rnrUrl != null) {
            String rnrReportFileName = htmlReport.createJsonReportFile();
            String rnrReportUrl = RnrService.uploadToRnr(rnrReportFileName, angularReportUrl, reportDetails.getCorrelationId());
            rnrUrl = rnrReportUrl;
            log.info("RnR URL: " + rnrReportUrl);
        }
        return angularReportUrl;
    }

    static void sendSummaryReportToSlack(String reportUrl, ReportDetails reportDetails) {

        String reportDate = "";
        ScenarioDetails scenarioDetails = reportDetails.getScenarios().peek();
        if (scenarioDetails != null) {
            reportDate = ReportingPlugin.reportDateFormat.format(scenarioDetails.getStartTime());
        }
        String title = "Automation Report - " + reportDate.replace(":", "-");
        ImageReport imageReport = new ImageReport(reportDetails);
        File summaryReport = imageReport.buildImageReport(title);
        String slackChannel1 = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
        String slackChannel2 = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
        if (StringUtils.isBlank(slackChannel1) && StringUtils.isBlank(slackChannel2)) {
            log.info("No Slack channel is set up.");

            return;
        }

        List<String> messages = new ArrayList<>();

        if (reportUrl != null) {
            messages.add("Test Report URL: " + reportUrl);
        }

        if (rnrUrl != null) {
            messages.add("RnR URL: " + rnrUrl);
        }

        if (StringUtils.isNotBlank(slackChannel1)) {
            SlackBotService.uploadReportToSlack(summaryReport, slackChannel1);

            if (!messages.isEmpty()) {
                SlackBotService.publishNotificationToSlack(slackChannel1, String.join("\n\n", messages));
            }
        }

        if (StringUtils.isNotBlank(slackChannel2)) {
            Boolean failed = false;
            if (reportDetails.getNumberOfFailedTests() > 0) {
                failed = true;
            }

            if (failed) {

                messages.add(0, "<!here> - There are test failures. Please see attached report below.");

                SlackBotService.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
                SlackBotService.uploadReportToSlack(summaryReport, slackChannel2);
            } else {
                String startTime = new Date(reportDetails.getStartTime()).toString();
                String url = ReportingPlugin.aeonConfiguration.getString("aeon.environment", "");
                messages.add(0, "Tests Passed for URL: " + url + " started at " + startTime);

                SlackBotService.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
            }
        }
    }
}
