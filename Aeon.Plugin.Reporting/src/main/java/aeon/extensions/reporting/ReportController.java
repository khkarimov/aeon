package aeon.extensions.reporting;

import aeon.core.common.helpers.StringUtils;
import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.services.ArtifactoryService;
import aeon.extensions.reporting.services.RnrService;
import aeon.extensions.reporting.services.SlackBotService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.List;

public class ReportController {

    private IConfiguration aeonConfiguration;
    private IConfiguration pluginConfiguration;
    private SlackBotService slackBotService;
    private ReportDetails reportDetails;

    private ArtifactoryService artifactoryService;
    private RnrService rnrService;

    private static Logger log = LogManager.getLogger(ReportController.class);
    private String rnrUrl;

    ReportController(IConfiguration pluginConfiguration, IConfiguration aeonConfiguration, ReportDetails reportDetails) {
        this.aeonConfiguration = aeonConfiguration;
        this.pluginConfiguration = pluginConfiguration;
        this.slackBotService = new SlackBotService(pluginConfiguration);

        this.rnrUrl = pluginConfiguration.getString(ReportingConfiguration.Keys.RNR_URL, "");

        this.reportDetails = reportDetails;
        artifactoryService =  new ArtifactoryService(pluginConfiguration);
        rnrService = new RnrService(aeonConfiguration, pluginConfiguration);
    }

    String writeReportsAndUpload() {
        HtmlAngularSummary htmlAngularSummary = new HtmlAngularSummary(pluginConfiguration, reportDetails);
        String angularReportFileName = htmlAngularSummary.createAngularReportFile();
        String angularReportUrl = artifactoryService.uploadToArtifactory(angularReportFileName);

        if (angularReportUrl != null) {
            log.info("Test Report URL: " + angularReportUrl);
        }
        if (rnrUrl != null) {
            String rnrReportFileName = htmlAngularSummary.createJsonReportFile();
            String rnrReportUrl = rnrService.uploadToRnR(rnrReportFileName, angularReportUrl, reportDetails.getCorrelationId());
            log.info("RnR URL: " + rnrReportUrl);
        }
        return angularReportUrl;
    }

    void sendSummaryReportToSlack(String reportUrl) {

        String reportDate = "";
        ScenarioDetails scenarioDetails = reportDetails.getScenarios().peek();
        if (scenarioDetails != null) {
            reportDate = ReportingPlugin.reportDateFormat.format(scenarioDetails.getStartTime());
        }
        String title = "Automation Report - " + reportDate.replace(":", "-");
        HtmlImageSummary htmlImageSummary = new HtmlImageSummary(aeonConfiguration, pluginConfiguration, reportDetails);
        File summaryReport = htmlImageSummary.constructSummaryImageFile(title);
        String slackChannel1 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_1, "");
        String slackChannel2 = pluginConfiguration.getString(ReportingConfiguration.Keys.CHANNEL_2, "");
        if (StringUtils.isBlank(slackChannel1) && StringUtils.isBlank(slackChannel2)) {
            log.info("No Slack channel is set up.");

            return;
        }

        List<String> messages = new ArrayList<>();

        if (reportUrl != null) {
            messages.add("Test Report URL: " + reportUrl);
        }

        if (this.rnrUrl != null) {
            messages.add("RnR URL: " + rnrUrl);
        }

        if (StringUtils.isNotBlank(slackChannel1)) {
            slackBotService.uploadReportToSlack(summaryReport, slackChannel1);
            Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");

            if (!messages.isEmpty()) {
                slackBotService.publishNotificationToSlack(slackChannel1, String.join("\n\n", messages));
            }
        }

        if (StringUtils.isNotBlank(slackChannel2)) {
            Boolean failed = false;
            if (reportDetails.getNumberOfFailedTests() > 0) {
                failed = true;
            }

            if (failed) {

                messages.add(0, "<!here> - There are test failures. Please see attached report below.");

                slackBotService.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
                slackBotService.uploadReportToSlack(summaryReport, slackChannel2);
                Utils.deleteFiles(Utils.getResourcesPath() + title + ".png");
            } else {
                String startTime = new Date(reportDetails.getStartTime()).toString();
                String url = aeonConfiguration.getString("aeon.environment", "");
                messages.add(0, "Tests Passed for URL: " + url + " started at " + startTime);

                slackBotService.publishNotificationToSlack(slackChannel2, String.join("\n\n", messages));
            }
        }
    }
}
