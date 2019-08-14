package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.extensions.reporting.extensions.IReportingExtension;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.apache.http.impl.client.HttpClients;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Reporting extension to send reports to Slack.
 */
@Extension
public class SlackReportingExtension implements IReportingExtension, IUploadListenerExtension {

    private final SlackReport slackReport;

    private static Logger log = LoggerFactory.getLogger(SlackReportingExtension.class);

    SlackReportingExtension(SlackReport slackReport) {
        this.slackReport = slackReport;
    }

    /**
     * Creates an instance for this extension.
     *
     * @return An instance of this extension.
     */
    public static Object createInstance() {
        IConfiguration configuration = new SlackConfiguration();

        try {
            configuration.loadConfiguration();
        } catch (IllegalAccessException | IOException e) {
            log.warn("Could not load plugin configuration.");
        }

        return new SlackReportingExtension(
                new SlackReport(
                        new ImageReport(configuration),
                        new SlackBotService(HttpClients.createDefault(), configuration),
                        configuration
                )
        );
    }

    @Override
    public void onUploadSucceeded(String url, String type, String label) {

        if (!type.equalsIgnoreCase("report")) {
            return;
        }

        if (label != null && !label.isEmpty()) {
            this.slackReport.postMessageToSlack(String.format("%s: %s", label, url));
        } else {
            this.slackReport.postMessageToSlack(url);
        }
    }

    @Override
    public void onReportGenerated(Report report, String jsonReport) {
        this.slackReport.sendImageReportToSlack(report);
    }
}
