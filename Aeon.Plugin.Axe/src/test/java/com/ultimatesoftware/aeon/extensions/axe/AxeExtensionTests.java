package com.ultimatesoftware.aeon.extensions.axe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ultimatesoftware.aeon.core.common.exceptions.UnableToTakeScreenshotException;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IWebAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AxeExtensionTests {

    private static final String CALLBACK_SCRIPT = "var callback = arguments[arguments.length - 1]; axe.run().then(function(result){callback(result);});";

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private IWebAdapter adapter;

    @Mock
    private Exception exception;

    @Mock
    private CloseableHttpResponse httpResponse;

    @Mock
    private StatusLine statusLine;

    @Mock
    private HttpEntity httpEntity;

    @Mock
    private AxeReportResponse axeReportResponse;

    @Mock
    private Logger log;

    @Captor
    private ArgumentCaptor<HttpPost> httpPostCaptor;

    private Map<String, Object> accessibilityReport = new HashMap<>();

    private BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

    private AxeExtension axeExtension;

    @BeforeEach
    void setUp() {
        this.axeExtension = new AxeExtension(this.configuration, this.httpClient, this.objectMapper);
        AxeExtension.log = this.log;
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = AxeExtension.createInstance();

        // Assert
        assertEquals(AxeExtension.class, extension.getClass());
    }

    @Test
    void onStartUp_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onBeforeStart_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onBeforeStart("correlationId", "suiteName");

        // Assert
        verifyZeroInteractions(this.httpClient);
    }

    @Test
    void onBeforeLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onBeforeLaunch(this.aeonConfiguration);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onAfterLaunch_isCalledWithAdapter_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.adapter);
    }

    @Test
    void onBeforeTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onBeforeTest("name");

        // Assert
        verifyZeroInteractions(this.httpClient);
    }

    @Test
    void onSucceededTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        Executable action = () -> this.axeExtension.onSucceededTest();

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void onSkippedTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onSkippedTest("name");

        // Assert
        verifyZeroInteractions(this.httpClient);
    }

    @Test
    void onFailedTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onFailedTest("reason", this.exception);

        // Assert
        verifyZeroInteractions(this.exception);
    }

    @Test
    void onBeforeStep_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        Executable action = () -> this.axeExtension.onBeforeStep("THEN the element is visible");

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void onDone_isCalledWithNoReportUrlSet_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onDone();

        // Assert
        verifyZeroInteractions(this.httpClient);
    }

    @Test
    void onDone_isCalledWithReportUrlSet_logsReportUrl() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        when(this.objectMapper.readValue("response-body", AxeReportResponse.class)).thenReturn(this.axeReportResponse);
        when(this.axeReportResponse.getReportUrl()).thenReturn("reportUrl");
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        this.axeExtension.runAccessibilityTests("pageName");

        // Act
        this.axeExtension.onDone();

        // Assert
        verify(this.log, times(1)).info("Dora Accessibility Report URL: {}", "reportUrl");
    }

    @Test
    void onExecutionEvent_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.axeExtension.onExecutionEvent("executionEvent", this.adapter);

        // Assert
        verifyZeroInteractions(this.adapter);
    }

    @Test
    void runAccessibilityTests_teamNameNotProvided_exceptionIsThrown() {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Team, product and page names need to be specified in order to submit an Axe report.", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
    }

    @Test
    void runAccessibilityTests_productNameNotProvided_exceptionIsThrown() {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Team, product and page names need to be specified in order to submit an Axe report.", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
    }

    @Test
    void runAccessibilityTests_emptyPageNameProvided_exceptionIsThrown() {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("");

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Team, product and page names need to be specified in order to submit an Axe report.", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
    }

    @Test
    void runAccessibilityTests_nullPageNameProvided_exceptionIsThrown() {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests(null);

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Team, product and page names need to be specified in order to submit an Axe report.", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
    }

    @Test
    void runAccessibilityTests_requestFails_exceptionIsThrown() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        IOException requestException = new IOException("error-message");
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenThrow(requestException);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Could not upload report to Axe report server (http://url): error-message.", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
    }

    @Test
    void runAccessibilityTests_requestNotSuccessful_exceptionIsThrown() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        when(this.statusLine.getStatusCode()).thenReturn(400);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        Exception thrownException = assertThrows(AxeException.class, action);
        assertEquals("Did not receive successful status code for Axe report upload. Received 400 with body: response-body", thrownException.getMessage());
        verify(this.adapter, times(1)).executeScript(anyString());
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
    }

    @Test
    void runAccessibilityTests_isCalled_runsAccessibilityTests() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        when(this.objectMapper.readValue("response-body", AxeReportResponse.class)).thenReturn(this.axeReportResponse);
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        verify(this.adapter, times(1)).executeScript(anyString());
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Axe report successfully uploaded for page {} and correlation ID {}.", "pageName", "correlationId");
        assertEquals("jsonString",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPostCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void runAccessibilityTests_cannotDeserializeResponse_throwsException() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenReturn(this.image);
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenThrow(new InvalidFormatException("error message", "", AxeReport.class));
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        Executable action = () -> this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        Exception exception = assertThrows(AxeException.class, action);
        assertEquals("Could not serialize Axe report.", exception.getMessage());
        assertTrue(exception.getCause() instanceof InvalidFormatException);
    }

    @Test
    void runAccessibilityTests_unableToTakeScreenshotException_logsErrorButSendsTheReport() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenThrow(new UnableToTakeScreenshotException());
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        when(this.objectMapper.readValue("response-body", AxeReportResponse.class)).thenReturn(this.axeReportResponse);
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        verify(this.adapter, times(1)).executeScript(anyString());
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Axe report successfully uploaded for page {} and correlation ID {}.", "pageName", "correlationId");
        verify(this.log, times(1)).error(eq("Unable to get screenshot"), any(UnableToTakeScreenshotException.class));
        assertEquals("jsonString",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPostCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }

    @Test
    void runAccessibilityTests_illegalArgumentException_logsErrorButSendsTheReport() throws IOException {

        // Arrange
        when(this.adapter.executeAsyncScript(CALLBACK_SCRIPT)).thenReturn(this.accessibilityReport);
        when(this.adapter.getScreenshot()).thenThrow(new IllegalArgumentException());
        doReturn("teamName").when(this.configuration).getString(AxeConfiguration.Keys.TEAM, "");
        doReturn("productName").when(this.configuration).getString(AxeConfiguration.Keys.PRODUCT, "");
        doReturn("branchName").when(this.configuration).getString(AxeConfiguration.Keys.BRANCH, "");
        doReturn("buildName").when(this.configuration).getString(AxeConfiguration.Keys.BUILD_NUMBER, "");
        doReturn("http://url").when(this.configuration).getString(AxeConfiguration.Keys.SERVER_URL, "");
        when(this.objectMapper.writeValueAsString(any(AxeReport.class))).thenReturn("jsonString");
        when(this.objectMapper.readValue("response-body", AxeReportResponse.class)).thenReturn(this.axeReportResponse);
        when(this.statusLine.getStatusCode()).thenReturn(200);
        when(this.httpResponse.getStatusLine()).thenReturn(this.statusLine);
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ByteArrayInputStream("response-body".getBytes()));
        when(this.httpClient.execute(this.httpPostCaptor.capture())).thenReturn(this.httpResponse);
        this.axeExtension.onStartUp(this.aeonConfiguration, "correlationId");
        this.axeExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.axeExtension.runAccessibilityTests("pageName");

        // Assert
        verify(this.adapter, times(1)).executeScript(anyString());
        verify(this.httpClient, times(1)).execute(this.httpPostCaptor.capture());
        verify(this.log, times(1)).info("Axe report successfully uploaded for page {} and correlation ID {}.", "pageName", "correlationId");
        verify(this.log, times(1)).error(eq("Unable to get screenshot"), any(IllegalArgumentException.class));
        assertEquals("jsonString",
                new BufferedReader(
                        new InputStreamReader(
                                this.httpPostCaptor.getValue().getEntity().getContent()
                        )
                ).lines().collect(Collectors.joining("\n")));
    }
}
