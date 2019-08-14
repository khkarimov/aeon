package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for publishing reports via a Slack bot on Slack.
 */
class SlackBotService {

    private final CloseableHttpClient httpClient;
    private String slackChatUrl;
    private String slackBotToken;
    private String slackUploadUrl;

    static Logger log = LoggerFactory.getLogger(SlackBotService.class);

    /**
     * Instantiates a new object of {@link SlackBotService}.
     *
     * @param configuration The Reporting plugin configuration object.
     */
    SlackBotService(CloseableHttpClient httpClient, IConfiguration configuration) {
        this.httpClient = httpClient;
        this.slackChatUrl = configuration.getString(SlackConfiguration.Keys.SLACK_CHAT_URL, "");
        this.slackBotToken = configuration.getString(SlackConfiguration.Keys.SLACK_BOT_TOKEN, "");
        this.slackUploadUrl = configuration.getString(SlackConfiguration.Keys.SLACK_UPLOAD_URL, "");
    }

    /**
     * Sends a notification to the given channel.
     *
     * @param channel The channel to send the message to.
     * @param message The message to send to the given channel.
     */
    void publishNotificationToSlack(String channel, String message) {
        HttpPost httpPost = new HttpPost(slackChatUrl);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("channel", channel));
        params.add(new BasicNameValuePair("token", slackBotToken));
        params.add(new BasicNameValuePair("text", message));
        params.add(new BasicNameValuePair("as_user", "true"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            executePost(httpPost, this.httpClient);
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to push notification to Slack", e);
        }
    }

    /**
     * Uploads a report in the form of an image to the given Slack channel.
     *
     * @param file    The image file to upload.
     * @param channel The channel to upload the image to.
     */
    void uploadReportToSlack(File file, String channel) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", file);
        builder.addTextBody("channels", channel);
        builder.addTextBody("token", slackBotToken);
        HttpEntity entity = builder.build();
        HttpPost httpPost = new HttpPost(slackUploadUrl);
        httpPost.setEntity(entity);
        executePost(httpPost, this.httpClient);
    }

    private void executePost(HttpPost httpPost, CloseableHttpClient client) {
        try (CloseableHttpResponse response = client.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("Couldn't post to Slack. Response status --> {}", response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            log.error("Failed to post to Slack.", e);
        }
    }
}
