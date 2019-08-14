package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SlackBotServiceTests {

    private SlackBotService slackBotService;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private IConfiguration configuration;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private Logger log;

    @Captor
    private ArgumentCaptor<HttpPost> httpPostCaptor;

    @Mock
    private File file;

    @BeforeEach
    void setup() throws IOException {
        doReturn("chat-url")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.SLACK_CHAT_URL, "");
        doReturn("token")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.SLACK_BOT_TOKEN, "");
        doReturn("upload-url")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.SLACK_UPLOAD_URL, "");
        this.slackBotService = new SlackBotService(this.httpClient, this.configuration);
        SlackBotService.log = this.log;

        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
    }

    @Test
    void publishNotificationToSlack_requestSuccessful_nothingIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);

        // Act
        this.slackBotService.publishNotificationToSlack("channel-name", "message");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verifyZeroInteractions(this.log);
        assertEquals(
                "channel=channel-name&token=token&text=message&as_user=true",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPostCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void publishNotificationToSlack_requestNotSuccessful_logsError() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);

        // Act
        this.slackBotService.publishNotificationToSlack("channel-name", "message");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log).error("Couldn't post to Slack. Response status --> {}", 400);
    }

    @Test
    void publishNotificationToSlack_requestFails_logsError() throws IOException {

        // Arrange
        IOException exception = new IOException("error-message");
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenThrow(exception);

        // Act
        this.slackBotService.publishNotificationToSlack("channel-name", "message");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log).error("Failed to post to Slack.", exception);
    }

    @Test
    void uploadReportToSlack_requestSuccessful_nothingIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);

        // Act
        this.slackBotService.uploadReportToSlack(this.file, "channel-name2");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verifyZeroInteractions(this.log);
    }

    @Test
    void uploadReportToSlack_requestNotSuccessful_logsError() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);

        // Act
        this.slackBotService.uploadReportToSlack(this.file, "channel-name2");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log).error("Couldn't post to Slack. Response status --> {}", 400);
    }

    @Test
    void uploadReportToSlack_requestFails_logsError() throws IOException {

        // Arrange
        IOException exception = new IOException("error-message");
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenThrow(exception);

        // Act
        this.slackBotService.uploadReportToSlack(this.file, "channel-name2");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log).error("Failed to post to Slack.", exception);
    }
}
