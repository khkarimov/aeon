package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.extensions.IUploaderExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.extensions.IReportingExtension;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pf4j.PluginManager;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportControllerTests {

    private ReportController reportController;

    @Mock
    private HtmlReport htmlReport;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private IReportingExtension reportingListener1;

    @Mock
    private IReportingExtension reportingListener2;

    @Mock
    private IUploaderExtension uploaderListener1;

    @Mock
    private IUploaderExtension uploaderListener2;

    @Mock
    private Report report;

    @Mock
    private Logger log;

    @BeforeEach
    void setup() {
        this.reportController = new ReportController(this.htmlReport);
        ReportController.log = this.log;

        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void writeReportsAndUpload_reportGenerationSuccessful_requestsUploadAndNotifiesListeners() {

        // Arrange
        doReturn(Arrays.asList(this.reportingListener1, this.reportingListener2))
                .when(this.pluginManager).getExtensions(IReportingExtension.class);
        doReturn(Arrays.asList(this.uploaderListener1, this.uploaderListener2))
                .when(this.pluginManager).getExtensions(IUploaderExtension.class);
        doReturn("reportFile")
                .when(this.htmlReport).createAngularReportFile();
        doReturn("report-url1")
                .when(this.uploaderListener1).onUploadRequested("reportFile", "report", "Test Report URL");
        doReturn("report-url2")
                .when(this.uploaderListener2).onUploadRequested("reportFile", "report", "Test Report URL");
        when(this.htmlReport.getReport()).thenReturn(this.report);
        when(this.htmlReport.getJsonReport()).thenReturn("jsonReport");

        // Act
        this.reportController.writeReportsAndUpload(this.report);

        // Assert
        verify(this.htmlReport, times(1)).prepareReport(this.report);
        verify(this.uploaderListener1, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verify(this.uploaderListener2, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verify(this.log, times(1)).info("Test Report URL: {}", "report-url2");
        verify(this.report, times(1)).setURL("report-url2");
        verify(this.reportingListener1, times(1)).onReportGenerated(this.report, "jsonReport");
        verify(this.reportingListener2, times(1)).onReportGenerated(this.report, "jsonReport");
    }

    @Test
    void writeReportsAndUpload_uploaderFailsButOtherOneSucceeds_notifiesListenersWithUrlSet() {

        // Arrange
        doReturn(Arrays.asList(this.reportingListener1, this.reportingListener2))
                .when(this.pluginManager).getExtensions(IReportingExtension.class);
        doReturn(Arrays.asList(this.uploaderListener1, this.uploaderListener2))
                .when(this.pluginManager).getExtensions(IUploaderExtension.class);
        doReturn("reportFile")
                .when(this.htmlReport).createAngularReportFile();
        doReturn("report-url1")
                .when(this.uploaderListener1).onUploadRequested("reportFile", "report", "Test Report URL");
        doReturn(null)
                .when(this.uploaderListener2).onUploadRequested("reportFile", "report", "Test Report URL");
        when(this.htmlReport.getReport()).thenReturn(this.report);
        when(this.htmlReport.getJsonReport()).thenReturn("jsonReport");

        // Act
        this.reportController.writeReportsAndUpload(this.report);

        // Assert
        verify(this.htmlReport, times(1)).prepareReport(this.report);
        verify(this.uploaderListener1, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verify(this.uploaderListener2, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verify(this.log, times(1)).info("Test Report URL: {}", "report-url1");
        verify(this.report, times(1)).setURL("report-url1");
        verify(this.reportingListener1, times(1)).onReportGenerated(this.report, "jsonReport");
        verify(this.reportingListener2, times(1)).onReportGenerated(this.report, "jsonReport");
    }

    @Test
    void writeReportsAndUpload_allUploadersFail_notifiesListenersWithoutUrlSet() {

        // Arrange
        doReturn(Arrays.asList(this.reportingListener1, this.reportingListener2))
                .when(this.pluginManager).getExtensions(IReportingExtension.class);
        doReturn(Arrays.asList(this.uploaderListener1, this.uploaderListener2))
                .when(this.pluginManager).getExtensions(IUploaderExtension.class);
        doReturn("reportFile")
                .when(this.htmlReport).createAngularReportFile();
        doReturn(null)
                .when(this.uploaderListener1).onUploadRequested("reportFile", "report", "Test Report URL");
        doReturn(null)
                .when(this.uploaderListener2).onUploadRequested("reportFile", "report", "Test Report URL");
        when(this.htmlReport.getReport()).thenReturn(this.report);
        when(this.htmlReport.getJsonReport()).thenReturn("jsonReport");

        // Act
        this.reportController.writeReportsAndUpload(this.report);

        // Assert
        verify(this.htmlReport, times(1)).prepareReport(this.report);
        verify(this.uploaderListener1, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verify(this.uploaderListener2, times(1)).onUploadRequested("reportFile", "report", "Test Report URL");
        verifyZeroInteractions(this.log);
        verifyZeroInteractions(this.report);
        verify(this.reportingListener1, times(1)).onReportGenerated(this.report, "jsonReport");
        verify(this.reportingListener2, times(1)).onReportGenerated(this.report, "jsonReport");
    }
}
