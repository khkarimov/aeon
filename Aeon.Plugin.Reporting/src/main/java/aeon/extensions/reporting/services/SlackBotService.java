package aeon.extensions.reporting.services;

import aeon.core.common.interfaces.IConfiguration;
import aeon.extensions.reporting.ReportingConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for publishing reports via a Slack bot on Slack.
 */
public class SlackBotService {

    private static Logger log = LoggerFactory.getLogger(SlackBotService.class);
    private String slackChatUrl;
    private String slackBotToken;
    private String slackUploadUrl;

    /**
     * Sets the Reporting plugin and Aeon configuration.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    public void setConfiguration(IConfiguration configuration) {
        this.slackChatUrl = configuration.getString(ReportingConfiguration.Keys.SLACK_CHAT_URL, "");
        this.slackBotToken = configuration.getString(ReportingConfiguration.Keys.SLACK_BOT_TOKEN, "");
        this.slackUploadUrl = configuration.getString(ReportingConfiguration.Keys.SLACK_UPLOAD_URL, "");
    }

    /**
     * Sends a notification to the given channel.
     *
     * @param channel The channel to send the message to.
     * @param message The message to send to the given channel.
     */
    public void publishNotificationToSlack(String channel, String message) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(slackChatUrl);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("channel", channel));
            params.add(new BasicNameValuePair("token", slackBotToken));
            params.add(new BasicNameValuePair("text", message));
            params.add(new BasicNameValuePair("as_user", "true"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            executePost(httpPost, client);
        } catch (Exception e) {
            log.error("Failed to push notification to Slack", e);
        }
    }

    /**
     * Uploads a report in the form of an image to the given Slack channel.
     *
     * @param fileName The name and path of the image to upload.
     * @param channel  The channel to upload the image to.
     */
    public void uploadReportToSlack(File fileName, String channel) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", fileName);
            builder.addTextBody("channels", channel);
            builder.addTextBody("token", slackBotToken);
            HttpEntity entity = builder.build();
            HttpPost httpPost = new HttpPost(slackUploadUrl);
            httpPost.setEntity(entity);
            HttpClient client = HttpClientBuilder.create().build();
            executePost(httpPost, client);
        } catch (Exception e) {
            log.error("Failed to publish message to Slack", e);
        }
    }

    private void executePost(HttpPost httpPost, HttpClient client) {
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("Couldn't post to Slack. Response status --> {}", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            log.error("Failed to post to Slack.", e);
        }
    }
}
