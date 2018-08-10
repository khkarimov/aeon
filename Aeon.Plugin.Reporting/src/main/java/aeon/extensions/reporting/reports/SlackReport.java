package aeon.extensions.reporting.reports;

import aeon.extensions.reporting.ReportDetails;
import aeon.extensions.reporting.ReportingConfiguration;
import aeon.extensions.reporting.ReportingPlugin;
import aeon.extensions.reporting.ScenarioDetails;
import aeon.extensions.reporting.services.SlackBotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlackReport {

    private String slackChannel1 = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
    private String slackChannel2 = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
    private String environment = ReportingPlugin.aeonConfiguration.getString("aeon.environment", "");

    private ReportDetails reportDetails;
    private List<String> messages;

    private static Logger log = LogManager.getLogger(SlackReport.class);

    public SlackReport(ReportDetails reportDetails) {
        this.reportDetails = reportDetails;
        messages = new ArrayList<>();
    }

    public void sendImageReportToSlack(String reportUrl, String rnrUrl) {
        if (!slackChannelsAreSet()) {
            log.info("No Slack channel is set up.");
            return;
        }

        File imageReport = getImageReport();

        addTestReportUrlMessage(reportUrl);
        addRnrUrlMessage(rnrUrl);

        postReportToChannel1(imageReport);

        if (reportHasFailures()) {
            postReportToChannel2(imageReport);
        }

    }

    private boolean slackChannelsAreSet() {
        return !(slackChannel1.isEmpty() || slackChannel2.isEmpty());
    }

    private void addTestReportUrlMessage(String reportUrl) {
        if (reportUrl != null) {
            messages.add("Test Report URL: " + reportUrl);
        }
    }

    private void addRnrUrlMessage(String rnrUrl) {
        if (rnrUrl != null) {
            messages.add("RnR URL: " + rnrUrl);
        }
    }

    private String getImageReportTitle() {
        String reportDate = "";
        ScenarioDetails scenarioDetails = reportDetails.getScenarios().peek();
        if (scenarioDetails != null) {
            reportDate = ReportingPlugin.reportDateFormat.format(scenarioDetails.getStartTime());
        }
        return "Automation Report - " + reportDate.replace(":", "-");
    }

    private File getImageReport() {
        String title = getImageReportTitle();

        ImageReport imageReport = new ImageReport(reportDetails);

        File summaryReport = imageReport.buildImageReport(title);

        return summaryReport;
    }

    private void postReportToChannel1(File imageReport) {
        if (!slackChannel1.isEmpty()) {
            SlackBotService.uploadReportToSlack(imageReport, slackChannel1);

            if (!messages.isEmpty()) {
                SlackBotService.publishNotificationToSlack(slackChannel1, fullMessage());
            }
        }
    }

    private void postReportToChannel2(File imageReport) {
        if (!slackChannel2.isEmpty()) {
            messages.add(0, "<!here> - There are test failures. Please see attached report below.");

            SlackBotService.publishNotificationToSlack(slackChannel2, fullMessage());
            SlackBotService.uploadReportToSlack(imageReport, slackChannel2);
        } else {
            String startTime = new Date(reportDetails.getStartTime()).toString();
            messages.add(0, String.format("Tests Passed for URL: %s started at %s.", environment, startTime));

            SlackBotService.publishNotificationToSlack(slackChannel2, fullMessage());
        }
    }

    private String fullMessage() {
        return String.join("\n\n", messages);
    }

    private boolean reportHasFailures() {
        return(reportDetails.getNumberOfFailedTests() > 0);
    }
}
