package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.ReportDetails;
import com.ultimatesoftware.aeon.extensions.reporting.reports.HtmlReport;
import com.ultimatesoftware.aeon.extensions.reporting.reports.SlackReport;
import com.ultimatesoftware.aeon.extensions.reporting.services.ArtifactoryService;
import com.ultimatesoftware.aeon.extensions.reporting.services.RnrService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {
    @InjectMocks
    ReportController reportController;

    @Mock
    HtmlReport htmlReport;
    @Mock
    SlackReport slackReport;
    @Mock
    ArtifactoryService artifactoryService;
    @Mock
    RnrService rnrService;
    @Mock
    IConfiguration configurationMock;
    @Mock
    IConfiguration aeonConfigurationMock;
    @Mock
    ReportDetails reportDetails;

    @Test
    public void setConfiguration_whenCalled_configurationsSet() {
        // Arrange

        // Act
        reportController.setConfiguration(configurationMock, aeonConfigurationMock);

        // Assert
        verify(htmlReport, times(1)).setConfiguration(configurationMock);
        verify(slackReport, times(1)).setConfiguration(configurationMock, aeonConfigurationMock);
        verify(artifactoryService, times(1)).setConfiguration(configurationMock);
        verify(rnrService, times(1)).setConfiguration(configurationMock, aeonConfigurationMock);
        verify(configurationMock, times(1)).getString(ReportingConfiguration.Keys.RNR_URL, "");
    }

    @Test
    public void writeReportsAndUpload_calledWithReportDetails_uploadsToArtifactory() {
        // Arrange
        when(htmlReport.createAngularReportFile()).thenReturn("Test123Path");
        when(reportDetails.getCorrelationId()).thenReturn("TestCorrelationId");
        when(configurationMock.getString(ReportingConfiguration.Keys.RNR_URL, "")).thenReturn("");
        reportController.setConfiguration(configurationMock, aeonConfigurationMock);

        // Act
        reportController.writeReportsAndUpload(reportDetails);

        // Assert
        verify(htmlReport, times(1)).prepareReport(reportDetails);
        verify(artifactoryService, times(1)).uploadToArtifactory("Test123Path");
    }

    @Test
    public void writeReportsAndUpload_calledWithoutRnRUrl_doesNotUpload() {
        // Arrange
        when(htmlReport.createAngularReportFile()).thenReturn("Test123Path");
        when(reportDetails.getCorrelationId()).thenReturn("TestCorrelationId");
        when(configurationMock.getString(ReportingConfiguration.Keys.RNR_URL, "")).thenReturn("");
        reportController.setConfiguration(configurationMock, aeonConfigurationMock);

        // Act
        reportController.writeReportsAndUpload(reportDetails);

        // Assert
        verify(rnrService, times(0)).uploadToRnr(any(), any(), any());
    }

    @Test
    public void writeReportsAndUpload_calledWithReportDetails_uploadsToRnR() {
        // Arrange
        when(htmlReport.createAngularReportFile()).thenReturn("Test123Path");
        when(htmlReport.createJsonReportFile()).thenReturn("JsonReportTestFile");
        when(reportDetails.getCorrelationId()).thenReturn("TestCorrelationId");
        when(artifactoryService.uploadToArtifactory(any())).thenReturn("ArtifactoryReportUrl");
        when(configurationMock.getString(ReportingConfiguration.Keys.RNR_URL, "")).thenReturn("NonEmptyUrl");
        reportController.setConfiguration(configurationMock, aeonConfigurationMock);

        // Act
        reportController.writeReportsAndUpload(reportDetails);

        // Assert
        verify(rnrService, times(1)).uploadToRnr("JsonReportTestFile", "ArtifactoryReportUrl", "TestCorrelationId");
    }

    @Test
    public void writeReportsAndUpload_calledWithReportDetails_thenSlackMessageSent() {
        // Arrange
        when(htmlReport.createAngularReportFile()).thenReturn("Test123Path");
        when(htmlReport.createJsonReportFile()).thenReturn("JsonReportTestFile");
        when(reportDetails.getCorrelationId()).thenReturn("TestCorrelationId");
        when(artifactoryService.uploadToArtifactory(any())).thenReturn("ArtifactoryReportUrl");
        when(rnrService.uploadToRnr(anyString(), anyString(), anyString())).thenReturn("FinalRnRUploadUrl");
        when(configurationMock.getString(ReportingConfiguration.Keys.RNR_URL, "")).thenReturn("NonEmptyURL");
        reportController.setConfiguration(configurationMock, aeonConfigurationMock);

        // Act
        reportController.writeReportsAndUpload(reportDetails);

        // Assert
        verify(slackReport, times(1)).setReportDetails(reportDetails);
        verify(slackReport, times(1)).sendImageReportToSlack("ArtifactoryReportUrl", "FinalRnRUploadUrl");
    }
}
