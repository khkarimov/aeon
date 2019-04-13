package com.ultimatesoftware.aeon.extensions.metrics;

import com.ultimatesoftware.aeon.core.extensions.IUploadListenerExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class MetricsReportingExtensionTests {

    private MetricsReportingExtension metricsReportingExtension;

    @Mock
    private MetricsService metricsService;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private IUploadListenerExtension listener1;

    @Mock
    private IUploadListenerExtension listener2;

    @Mock
    private Report report;

    @Mock
    private Logger log;

    @BeforeEach
    void setup() {
        this.metricsReportingExtension = new MetricsReportingExtension(this.metricsService);
        MetricsReportingExtension.log = this.log;

        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = MetricsReportingExtension.createInstance();

        // Assert
        assertEquals(MetricsReportingExtension.class, extension.getClass());
    }

    @Test
    void onReportGenerated_uploadSuccessful_logsMessageAndNotifiesListeners() {

        // Arrange
        when(this.report.getCorrelationId()).thenReturn("correlationId");
        when(this.report.getURL()).thenReturn("report-url");
        when(this.metricsService.createJsonReportFile("jsonReport", "correlationId")).thenReturn("fileName");
        when(this.metricsService.uploadToMetricsGateway("fileName", "report-url", "correlationId")).thenReturn("rnr-url");
        when(this.pluginManager.getExtensions(IUploadListenerExtension.class))
                .thenReturn(Arrays.asList(this.listener1, this.listener2));

        // Act
        this.metricsReportingExtension.onReportGenerated(this.report, "jsonReport");

        // Assert
        verify(this.metricsService, times(1)).createJsonReportFile("jsonReport", "correlationId");
        verify(this.metricsService, times(1)).uploadToMetricsGateway("fileName", "report-url", "correlationId");
        verify(this.listener1, times(1)).onUploadSucceeded("rnr-url", "report", "RnR URL");
        verify(this.listener2, times(1)).onUploadSucceeded("rnr-url", "report", "RnR URL");
        verify(this.log, times(1)).info("RnR URL: {}", "rnr-url");
    }

    @Test
    void onReportGenerated_uploadNotSuccessful_doesNotLogMessageAndDoesNotNotifyListeners() {

        // Arrange
        when(this.report.getCorrelationId()).thenReturn("correlationId");
        when(this.report.getURL()).thenReturn("report-url");
        when(this.metricsService.createJsonReportFile("jsonReport", "correlationId")).thenReturn("fileName");
        when(this.metricsService.uploadToMetricsGateway("fileName", "report-url", "correlationId")).thenReturn(null);

        // Act
        this.metricsReportingExtension.onReportGenerated(this.report, "jsonReport");

        // Assert
        verify(this.metricsService, times(1)).createJsonReportFile("jsonReport", "correlationId");
        verify(this.metricsService, times(1)).uploadToMetricsGateway("fileName", "report-url", "correlationId");
        verifyZeroInteractions(this.pluginManager);
        verifyZeroInteractions(this.listener1);
        verifyZeroInteractions(this.listener2);
        verifyZeroInteractions(this.log);
    }
}
