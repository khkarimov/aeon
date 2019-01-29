package aeon.extensions.reporting.services;

import aeon.extensions.reporting.ReportingConfiguration;
import aeon.extensions.reporting.ReportingPlugin;
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

public class SlackBotService {

    private static Logger log = LoggerFactory.getLogger(SlackBotService.class);
    private static String slackChatUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.SLACK_CHAT_URL, "");
    private static String slackBotToken = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.SLACK_BOT_TOKEN, "");
    private static String slackUploadUrl = ReportingPlugin.configuration.getString(ReportingConfiguration.Keys.SLACK_UPLOAD_URL, "");

    public static void publishNotificationToSlack(String channel, String message) {
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

    public static void uploadReportToSlack(File fileName, String channel) {
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

    private static void executePost(HttpPost httpPost, HttpClient client) {
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                log.error("Couldn't post to Slack. Response status --> {}", Integer.toString(response.getStatusLine().getStatusCode()));
            }
        } catch (Exception e) {
            log.error("Failed to post to Slack.", e);
        }
    }
}
