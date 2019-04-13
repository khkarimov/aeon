package com.ultimatesoftware.aeon.extensions.artifactory;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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

import java.io.*;
import java.util.stream.Collectors;

import static org.apache.http.protocol.HTTP.USER_AGENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ArtifactoryServiceTests {

    private ArtifactoryService artifactoryService;

    @Mock
    private HttpClientBuilder httpClientBuilder;

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
    private ArgumentCaptor<HttpPut> httpPutCaptor;

    @Captor
    private ArgumentCaptor<CredentialsProvider> credentialsProviderCaptor;

    private File file;
    private String fileName;

    @BeforeEach
    void setup() throws IOException {
        doReturn("artifactory-url")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_URL, "");
        doReturn("artifactory-path")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_PATH, "");
        doReturn("artifactory-username")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        doReturn("artifactory-password")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_PASSWORD, "");
        this.artifactoryService = new ArtifactoryService(this.httpClientBuilder, this.configuration);
        ArtifactoryService.log = this.log;

        this.file = File.createTempFile("artifactory", ".txt");
        this.file.deleteOnExit();
        this.fileName = this.file.getName();
        PrintWriter out = new PrintWriter(this.file);
        out.println("test-file-content");
        out.close();
    }

    @Test
    void uploadToArtifactory_requestSuccessful_nothingIsLogged() throws IOException {

        // Arrange
        when(this.httpClient.execute(this.httpPutCaptor.capture())).thenReturn(this.httpResponse);
        when(this.httpClientBuilder.build()).thenReturn(this.httpClient);
        when(this.statusLine.getStatusCode()).thenReturn(201);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpClientBuilder.setDefaultCredentialsProvider(this.credentialsProviderCaptor.capture())).thenReturn(this.httpClientBuilder);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPutCaptor.capture());
        verifyZeroInteractions(this.log);
        assertEquals("artifactory-url/artifactory-path/" + this.fileName, url);
        assertEquals("artifactory-username", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getUserPrincipal().getName());
        assertEquals("artifactory-password", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getPassword());
        assertEquals(USER_AGENT, this.httpPutCaptor.getValue().getFirstHeader("User-Agent").getValue());
        assertEquals("text/html", this.httpPutCaptor.getValue().getFirstHeader("Content-type").getValue());
        assertEquals("artifactory-url/artifactory-path/" + this.fileName, this.httpPutCaptor.getValue().getURI().toString());
        assertEquals(
                "test-file-content",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPutCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void uploadToArtifactory_requestNotSuccessful_errorIsLogged() throws IOException {

        // Arrange
        when(this.httpClient.execute(this.httpPutCaptor.capture())).thenReturn(this.httpResponse);
        when(this.httpClientBuilder.build()).thenReturn(this.httpClient);
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpClientBuilder.setDefaultCredentialsProvider(this.credentialsProviderCaptor.capture())).thenReturn(this.httpClientBuilder);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPutCaptor.capture());
        verify(this.log, times(1)).error("Did not receive successful status code for artifactory upload. Received: {}.", 400);
        assertNull(url);
        assertEquals("artifactory-username", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getUserPrincipal().getName());
        assertEquals("artifactory-password", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getPassword());
        assertEquals(USER_AGENT, this.httpPutCaptor.getValue().getFirstHeader("User-Agent").getValue());
        assertEquals("text/html", this.httpPutCaptor.getValue().getFirstHeader("Content-type").getValue());
        assertEquals("artifactory-url/artifactory-path/" + this.fileName, this.httpPutCaptor.getValue().getURI().toString());
        assertEquals(
                "test-file-content",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPutCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void uploadToArtifactory_requestFails_errorIsLogged() throws IOException {

        // Arrange
        when(this.httpClient.execute(this.httpPutCaptor.capture())).thenReturn(this.httpResponse);
        when(this.httpClientBuilder.build()).thenReturn(this.httpClient);
        IOException exception = new IOException("error-message");
        when(this.httpClient.execute(this.httpPutCaptor.capture())).thenThrow(exception);
        when(this.httpClientBuilder.setDefaultCredentialsProvider(this.credentialsProviderCaptor.capture())).thenReturn(this.httpClientBuilder);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, times(1)).execute(this.httpPutCaptor.capture());
        verify(this.log, times(1)).error("Could not upload report file to artifactory: error-message", exception);
        assertNull(url);
        assertEquals("artifactory-username", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getUserPrincipal().getName());
        assertEquals("artifactory-password", this.credentialsProviderCaptor.getValue().getCredentials(AuthScope.ANY).getPassword());
        assertEquals(USER_AGENT, this.httpPutCaptor.getValue().getFirstHeader("User-Agent").getValue());
        assertEquals("text/html", this.httpPutCaptor.getValue().getFirstHeader("Content-type").getValue());
        assertEquals("artifactory-url/artifactory-path/" + this.fileName, this.httpPutCaptor.getValue().getURI().toString());
        assertEquals(
                "test-file-content",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPutCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void uploadToArtifactory_artifactoryUrlNotSet_logsMessageAndDoesNotUploadFile() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_URL, "");
        this.artifactoryService = new ArtifactoryService(this.httpClientBuilder, this.configuration);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, never()).execute(any());
        verify(this.log, times(1)).info("Not all artifactory properties set, cancelling file upload.");
        assertNull(url);
    }

    @Test
    void uploadToArtifactory_artifactoryPathNotSet_logsMessageAndDoesNotUploadFile() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_PATH, "");
        this.artifactoryService = new ArtifactoryService(this.httpClientBuilder, this.configuration);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, never()).execute(any());
        verify(this.log, times(1)).info("Not all artifactory properties set, cancelling file upload.");
        assertNull(url);
    }

    @Test
    void uploadToArtifactory_artifactoryUsernameNotSet_logsMessageAndDoesNotUploadFile() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_USERNAME, "");
        this.artifactoryService = new ArtifactoryService(this.httpClientBuilder, this.configuration);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, never()).execute(any());
        verify(this.log, times(1)).info("Not all artifactory properties set, cancelling file upload.");
        assertNull(url);
    }

    @Test
    void uploadToArtifactory_artifactoryPasswordNotSet_logsMessageAndDoesNotUploadFile() throws IOException {

        // Arrange
        doReturn("")
                .when(this.configuration)
                .getString(ArtifactoryConfiguration.Keys.ARTIFACTORY_PASSWORD, "");
        this.artifactoryService = new ArtifactoryService(this.httpClientBuilder, this.configuration);

        // Act
        String url = this.artifactoryService.uploadToArtifactory(this.file.getAbsolutePath());

        // Assert
        verify(this.httpClient, never()).execute(any());
        verify(this.log, times(1)).info("Not all artifactory properties set, cancelling file upload.");
        assertNull(url);
    }
}
