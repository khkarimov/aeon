package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SlackReportingExtensionTests {

    private SlackReportingExtension slackReportingExtension;

    @Mock
    private SlackReport slackReport;

    @Mock
    private Report report;

    @BeforeEach
    void setup() {
        this.slackReportingExtension = new SlackReportingExtension(this.slackReport);
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = SlackReportingExtension.createInstance();

        // Assert
        assertEquals(SlackReportingExtension.class, extension.getClass());
    }

    @Test
    void onUploadSucceeded_calledWithReportAndWithoutLabel_postsMessageToSlack() {

        // Arrange

        // Act
        this.slackReportingExtension.onUploadSucceeded("url", "report", null);

        // Assert
        verify(this.slackReport, times(1)).postMessageToSlack("url");
    }

    @Test
    void onUploadSucceeded_calledWithReportAndWithEmptyLabel_postsMessageToSlack() {

        // Arrange

        // Act
        this.slackReportingExtension.onUploadSucceeded("url", "report", "");

        // Assert
        verify(this.slackReport, times(1)).postMessageToSlack("url");
    }

    @Test
    void onUploadSucceeded_calledWithReportAndWithLabel_postsMessageToSlack() {

        // Arrange

        // Act
        this.slackReportingExtension.onUploadSucceeded("url", "report", "Label");

        // Assert
        verify(this.slackReport, times(1)).postMessageToSlack("Label: url");
    }

    @Test
    void onUploadSucceeded_calledWithDifferentType_doesNotPostToSlack() {

        // Arrange

        // Act
        this.slackReportingExtension.onUploadSucceeded("url", "something", "Label");

        // Assert
        verify(this.slackReport, times(0)).postMessageToSlack(any());
    }


    @Test
    void onReportGenerated_calledWithReport_postsReportToSlack() {

        // Arrange

        // Act
        this.slackReportingExtension.onReportGenerated(this.report, "jsonReport");

        // Assert
        verify(this.slackReport, times(1)).sendImageReportToSlack(this.report);
    }
}
