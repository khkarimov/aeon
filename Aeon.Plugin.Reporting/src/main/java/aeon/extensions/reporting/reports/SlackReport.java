package aeon.extensions.reporting.reports;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportingConfiguration;
import aeon.extensions.reporting.models.ReportDetails;
import aeon.extensions.reporting.models.ScenarioDetails;
import aeon.extensions.reporting.services.SlackBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class SlackReport {

    private String slackChannel1;
    private String slackChannel2;
    private String environment;

    private ReportDetails reportDetails;
    private List<String> messages = new ArrayList<>();

    private ImageReport imageReport;
    private SlackBotService slackBotService;

    private final SimpleDateFormat reportDateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    private static Logger log = LoggerFactory.getLogger(SlackReport.class);

    /**
     * Creates a new Slack report.
     *
     * @param imageReport     The image report to send.
     * @param slackBotService The slack bot service to use.
     */
    public SlackReport(ImageReport imageReport, SlackBotService slackBotService) {
        this.imageReport = imageReport;
        this.slackBotService = slackBotService;
    }

    /**
     * Sets the Reporting plugin and Aeon configuration.
     *
     * @param configuration     The Reporting plugin configuration object.
     * @param aeonConfiguration The Aeon configuration object.
     */
    public void setConfiguration(IConfiguration configuration, IConfiguration aeonConfiguration) {

        this.imageReport.setConfiguration(configuration, aeonConfiguration);
        this.slackBotService.setConfiguration(configuration);

        this.slackChannel1 = configuration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
        this.slackChannel2 = configuration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
        this.environment = aeonConfiguration.getString("aeon.environment", "");
    }

    /**
     * Sets the report details.
     *
     * @param reportDetails The report details.
     */
    public void setReportDetails(ReportDetails reportDetails) {
        this.reportDetails = reportDetails;
        this.imageReport.setReportDetails(reportDetails);
    }

    /**
     * Creates the Slack reports and sends them to the configured channels.
     *
     * @param reportUrl The URL of the HTML report to link.
     * @param rnrUrl    The URL of the RocknRoly report to link.
     */
    public void sendImageReportToSlack(String reportUrl, String rnrUrl) {
        if (!slackChannelsAreSet()) {
            log.info("No Slack channel is set up.");
            return;
        }

        File imageReportFile = getImageReport();

        addTestReportUrlMessage(reportUrl);
        addRnrUrlMessage(rnrUrl);

        postReportToChannel1(imageReportFile);

        if (reportHasFailures()) {
            postReportToChannel2(imageReportFile);
        }

    }

    private boolean slackChannelsAreSet() {
        return !(slackChannel1.isEmpty() && slackChannel2.isEmpty());
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
            reportDate = reportDateFormat.format(scenarioDetails.getStartTime());
        }
        return "Automation Report - " + reportDate.replace(":", "-");
    }

    private File getImageReport() {
        String title = getImageReportTitle();

        return this.imageReport.buildImageReport(title);
    }

    private void postReportToChannel1(File imageReport) {
        if (!slackChannel1.isEmpty()) {
            this.slackBotService.uploadReportToSlack(imageReport, slackChannel1);

            if (!messages.isEmpty()) {
                this.slackBotService.publishNotificationToSlack(slackChannel1, fullMessage());
            }
        }
    }

    private void postReportToChannel2(File imageReport) {
        if (!slackChannel2.isEmpty()) {
            messages.add(0, "<!here> - There are test failures. Please see attached report below.");

            this.slackBotService.publishNotificationToSlack(slackChannel2, fullMessage());
            this.slackBotService.uploadReportToSlack(imageReport, slackChannel2);
        } else {
            String startTime = new Date(reportDetails.getStartTime()).toString();
            messages.add(0, String.format("Tests Passed for URL: %s started at %s.", environment, startTime));

            this.slackBotService.publishNotificationToSlack(slackChannel2, fullMessage());
        }
    }

    private String fullMessage() {
        return String.join("\n\n", messages);
    }

    private boolean reportHasFailures() {
        return (reportDetails.getNumberOfFailedTests() > 0);
    }
}
