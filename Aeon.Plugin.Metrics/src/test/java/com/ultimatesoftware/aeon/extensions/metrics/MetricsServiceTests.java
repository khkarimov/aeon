package com.ultimatesoftware.aeon.extensions.metrics;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import org.apache.http.HttpEntity;
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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class MetricsServiceTests {

    private MetricsService metricsService;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private IConfiguration configuration;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity httpEntity;

    @Mock
    private Logger log;

    @Captor
    private ArgumentCaptor<HttpPost> httpPostCaptor;

    @BeforeEach
    void setup() {
        doReturn("Chrome")
                .when(this.configuration)
                .getString("aeon.browser", "");
        doReturn("google.com")
                .when(this.configuration)
                .getString("aeon.environment", "");
        doReturn("branch-name")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.BRANCH, "");
        doReturn("product-name")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.PRODUCT, "");
        doReturn("team-name")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.TEAM, "");
        doReturn("test-type")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.TYPE, "");
        doReturn("build-number")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("metrics-url")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.METRICS_GATEWAY_URL, "");
        doReturn("rnr-url")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.RNR_URL, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);
        MetricsService.log = this.log;
    }

    @Test
    void uploadToMetricsGateway_requestSuccessful_nothingIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verifyZeroInteractions(this.log);
    }

    @Test
    void uploadToMetricsGateway_requestNotSuccessful_errorIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).error("Did not receive successful status code for metrics upload. Received {} with body: {}", 400, "response-body");
    }

    @Test
    void uploadToMetricsGateway_requestFails_errorIsLogged() throws IOException {

        // Arrange
        IOException exception = new IOException("error-message");
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenThrow(exception);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).error("Could not upload report to Metrics Gateway (metrics-url): error-message.", exception);
    }

    @Test
    void uploadToMetricsGateway_productNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.PRODUCT, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
    }

    @Test
    void uploadToMetricsGateway_teamNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.TEAM, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
    }

    @Test
    void uploadToMetricsGateway_branchNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.BRANCH, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
    }

    @Test
    void uploadToMetricsGateway_typeNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.TYPE, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
    }

    @Test
    void uploadToMetricsGateway_metricsUrlNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(MetricsConfiguration.Keys.METRICS_GATEWAY_URL, "");
        this.metricsService = new MetricsService(this.httpClient, this.configuration);

        // Act
        this.metricsService.uploadToMetricsGateway("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all metrics properties are set, cancelling upload to Metrics Gateway.");
    }
}
