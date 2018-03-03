package aeon.extensions.reporting;

import aeon.core.common.interfaces.IConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlackBot {

    private static Logger log = LogManager.getLogger(SlackBot.class);

    private IConfiguration configuration;

    SlackBot(IConfiguration pluginConfiguration) {
        configuration = pluginConfiguration;
    }

    public void publishNotificationToSlack(String channel, String message) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(configuration.getString(ReportingConfiguration.Keys.SLACK_CHAT_URL, ""));

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("channel", channel));
            params.add(new BasicNameValuePair("token", configuration.getString(ReportingConfiguration.Keys.SLACK_TOKEN, "")));
            params.add(new BasicNameValuePair("text", message));
            params.add(new BasicNameValuePair("as_user", "Launch Dev Logging"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            executePost(httpPost, client);
        } catch (Exception e) {
            log.error("Failed to push notification to Slack", e);
        }
    }

    public void publishMessageToSlack(File fileName, String channel) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", fileName);
            builder.addTextBody("channels", channel);
            builder.addTextBody("token", configuration.getString(ReportingConfiguration.Keys.SLACK_TOKEN, ""));
            HttpEntity entity = builder.build();
            HttpPost httpPost = new HttpPost(configuration.getString(ReportingConfiguration.Keys.SLACK_UPLOAD_URL, "https://slack.com/api/files.upload"));
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
                log.error("Couldn't post to Slack. Response status --> {}", Integer.toString(response.getStatusLine().getStatusCode()));
            }
        } catch (Exception e) {
            log.error("Failed to post to Slack.", e);
        }
    }
}