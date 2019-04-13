package com.ultimatesoftware.aeon.extensions.rnr;

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

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class RnrServiceTests {

    private RnrService rnrService;

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
                .getString(RnrConfiguration.Keys.BRANCH, "");
        doReturn("product-name")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.PRODUCT, "");
        doReturn("team-name")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.TEAM, "");
        doReturn("test-type")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.TYPE, "");
        doReturn("rnr-url")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.RNR_URL, "");
        this.rnrService = new RnrService(this.httpClient, this.configuration);
        RnrService.log = this.log;
    }

    @Test
    void uploadToRnr_requestSuccessful_nothingIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(202);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verifyZeroInteractions(this.log);
    }

    @Test
    void uploadToRnr_requestNotSuccessful_errorIsLogged() throws IOException {

        // Arrange
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).error("Did not receive successful status code for RnR upload. Received: {}.", 400);
    }

    @Test
    void uploadToRnr_requestFails_errorIsLogged() throws IOException {

        // Arrange
        IOException exception = new IOException("error-message");
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenThrow(exception);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).error("Could not upload report to RnR (rnr-url/testsuite): error-message.", exception);
    }

    @Test
    void uploadToRnr_productNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.PRODUCT, "");
        this.rnrService = new RnrService(this.httpClient, this.configuration);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all RnR properties are set, cancelling upload to RnR.");
    }

    @Test
    void uploadToRnr_teamNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.TEAM, "");
        this.rnrService = new RnrService(this.httpClient, this.configuration);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all RnR properties are set, cancelling upload to RnR.");
    }

    @Test
    void uploadToRnr_branchNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.BRANCH, "");
        this.rnrService = new RnrService(this.httpClient, this.configuration);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all RnR properties are set, cancelling upload to RnR.");
    }

    @Test
    void uploadToRnr_typeNotSet_logsMessageAndDoesNotUploadReport() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(RnrConfiguration.Keys.TYPE, "");
        this.rnrService = new RnrService(this.httpClient, this.configuration);

        // Act
        this.rnrService.uploadToRnr("fileName", "reportUrl", "correlationId");

        // Assert
        verify(this.httpClient, never()).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Not all RnR properties are set, cancelling upload to RnR.");
    }
}
