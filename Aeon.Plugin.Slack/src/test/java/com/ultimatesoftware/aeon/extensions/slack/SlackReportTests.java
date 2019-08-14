package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SlackReportTests {

    private SlackReport slackReport;

    @Mock
    private ImageReport imageReport;

    @Mock
    private SlackBotService slackBotService;

    @Mock
    private IConfiguration configuration;

    @Mock
    private File file;

    @Test
    void postMessageToSlack_bothSlackChannelsSetButReportHasNoFailures_postsMessageOnlyToChannel1() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);

        // Act
        this.slackReport.postMessageToSlack("message");

        // Assert
        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel1", "message");

        verify(this.slackBotService, never())
                .publishNotificationToSlack(eq("channel2"), any());
    }

    @Test
    void postMessageToSlack_bothSlackChannelsSetAndReportHasFailures_postsMessageToBothChannels() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        report.getCounts().setFailed(2);
        this.slackReport.sendImageReportToSlack(report);

        // Act
        this.slackReport.postMessageToSlack("message");

        // Assert
        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel1", "message");

        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "message");
    }

    @Test
    void postMessageToSlack_onlyChannel2SetAndReportHasNoFailures_postsMessageToNoChannel() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");


        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);

        // Act
        this.slackReport.postMessageToSlack("message2");

        // Assert
        verify(this.slackBotService, never())
                .publishNotificationToSlack(any(), any());
    }

    @Test
    void postMessageToSlack_onlyChannel1SetAndReportHasNoFailures_postsMessageOnlyToChannel1() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");


        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);

        // Act
        this.slackReport.postMessageToSlack("message4");

        // Assert
        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel1", "message4");
        verify(this.slackBotService, never())
                .publishNotificationToSlack(eq("channel2"), any());
    }

    @Test
    void sendImageReportToSlack_noSlackChannelSet_logsMessage() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        SlackReport.log = mock(Logger.class);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, never())
                .uploadReportToSlack(any(), any());
        verify(SlackReport.log, times(1)).info("No Slack channel is set up.");
    }

    @Test
    void sendImageReportToSlack_bothSlackChannelsSetAndReportHasFailures_postsMessageToBothChannels() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        report.getCounts().setFailed(1);

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "<!here> - There are test failures. Please see attached report below.");
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel2");
    }

    @Test
    void sendImageReportToSlack_reportHasFailures_postsMessageToBothChannelsWithCorrectReportTitle() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = dateFormatter.format(new Date());
        TestCase testCase = new TestCase();
        testCase.setStarted(date);
        Report report = new Report();
        report.getCounts().setFailed(1);
        report.setSequence(Collections.singletonList(testCase));

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - " + date.replace(":", "-")))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "<!here> - There are test failures. Please see attached report below.");
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel2");
    }

    @Test
    void sendImageReportToSlack_reportHasFailureAndReportHasUrl_postsMessageWithReportUrl() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        report.getCounts().setFailed(1);
        report.setURL("url");

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "<!here> - There are test failures. Please see attached report below.");
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel2");
        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "Test Report URL: url");
    }

    @Test
    void sendImageReportToSlack_reportHasFailureAndReportHasEmptyUrl_doesNotPostMessageWithReportUrl() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        report.getCounts().setFailed(1);
        report.setURL("");

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, times(1))
                .publishNotificationToSlack("channel2", "<!here> - There are test failures. Please see attached report below.");
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel2");
        verify(this.slackBotService, never())
                .publishNotificationToSlack("channel2", "Test Report URL: url");
    }

    @Test
    void sendImageReportToSlack_bothSlackChannelsSetButReportHasNoFailures_postsMessageOnlyToChannel1() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, never())
                .publishNotificationToSlack(eq("channel2"), any());
        verify(this.slackBotService, never())
                .uploadReportToSlack(any(), eq("channel2"));
    }

    @Test
    void sendImageReportToSlack_onlyChannel2SetButReportHasNoFailures_postsMessageToNoChannel() {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("channel2")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, never())
                .uploadReportToSlack(any(), any());

        verify(this.slackBotService, never())
                .publishNotificationToSlack(any(), any());
    }

    @Test
    void sendImageReportToSlack_onlyChannel1SetAndReportHasNoFailures_postsMessageOnlyToChannel1() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, never())
                .publishNotificationToSlack(any(), eq("channel2"));
    }

    @Test
    void sendImageReportToSlack_onlyChannel1SetAndReportHasFailures_postsMessageOnlyToChannel1() {

        // Arrange
        doReturn("channel1")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_1, "");
        doReturn("")
                .when(this.configuration)
                .getString(SlackConfiguration.Keys.CHANNEL_2, "");

        this.slackReport = new SlackReport(
                this.imageReport,
                this.slackBotService,
                this.configuration);
        Report report = new Report();
        report.getCounts().setFailed(2);

        when(this.imageReport.buildReportHtml(report))
                .thenReturn("html");
        when(this.imageReport.htmlToPngFile("html", "Automation Report - "))
                .thenReturn(this.file);

        // Act
        this.slackReport.sendImageReportToSlack(report);

        // Assert
        verify(this.slackBotService, times(1))
                .uploadReportToSlack(this.file, "channel1");

        verify(this.slackBotService, never())
                .publishNotificationToSlack(any(), eq("channel2"));
    }
}
