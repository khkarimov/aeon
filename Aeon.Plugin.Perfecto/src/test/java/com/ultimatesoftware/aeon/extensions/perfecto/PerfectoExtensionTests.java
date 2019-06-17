package com.ultimatesoftware.aeon.extensions.perfecto;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.CustomField;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResult;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.common.exceptions.ScriptExecutionException;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumAdapter;
import com.ultimatesoftware.aeon.extensions.selenium.SeleniumConfiguration;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.MutableCapabilities;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class PerfectoExtensionTests {

    @Mock
    private ReportiumClientFactory reportiumClientFactory;

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private SeleniumAdapter adapter;

    @Mock
    private ReportiumClient reportiumClient;

    @Mock
    private AndroidDriver androidDriver;

    @Mock
    private Logger log;

    @Captor
    private ArgumentCaptor<PerfectoExecutionContext> executionContextCaptor;

    @Captor
    private ArgumentCaptor<TestContext> testContextCaptor;

    @Captor
    private ArgumentCaptor<TestResult> testResultCaptor;

    @Mock
    private MutableCapabilities mutableCapabilities;

    private PerfectoExtension perfectoExtension;

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = PerfectoExtension.createInstance();

        // Assert
        assertEquals(PerfectoExtension.class, extension.getClass());
    }

    @Test
    void onStartUp_isCalled_setsCorrelationId() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);

        // Act
        this.perfectoExtension.onStartUp(this.aeonConfiguration, "correlationId");

        // Assert
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        Iterator<CustomField> iterator = this.executionContextCaptor.getValue().getCustomFields().iterator();
        assertEquals("correlationId", iterator.next().getValue());
        assertEquals(Aeon.getVersion(), iterator.next().getValue());
    }

    @Test
    void onBeforeStart_isCalled_setsCorrelationIdAndSuiteName() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);

        // Act
        this.perfectoExtension.onBeforeStart("correlationId", "suiteName");

        // Assert
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        Iterator<CustomField> iterator = this.executionContextCaptor.getValue().getCustomFields().iterator();
        assertEquals("correlationId", iterator.next().getValue());
        assertEquals(Aeon.getVersion(), iterator.next().getValue());
        assertEquals("suiteName", iterator.next().getValue());
    }

    @Test
    void onBeforeLaunch_isCalled_doesNotDoAnything() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;

        // Act
        this.perfectoExtension.onBeforeLaunch(this.aeonConfiguration);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onAfterLaunch_isCalled_setsConfigurationProperties() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.aeonConfiguration.getConfigurationKeys()).thenReturn(Arrays.asList("config1", "config2"));
        doReturn("value1")
                .when(this.aeonConfiguration)
                .getString("config1", null);
        doReturn("value2")
                .when(this.aeonConfiguration)
                .getString("config2", null);
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);

        // Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Assert
        Iterator<CustomField> iterator = this.executionContextCaptor.getValue().getCustomFields().iterator();
        assertEquals("value1", iterator.next().getValue());
        assertEquals("value2", iterator.next().getValue());
        assertEquals(Aeon.getVersion(), iterator.next().getValue());
        assertEquals(this.androidDriver, this.executionContextCaptor.getValue().getWebdriver());

        // Validate whether reportiumClient was set
        this.perfectoExtension.onBeforeStep("step2");
        verify(this.reportiumClient, times(1)).stepStart("step2");
    }

    @Test
    void onBeforeTest_tagsArePassed_setsTagsInContext() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeTest("test-name", "tag1", "tag2");

        // Assert
        verify(this.reportiumClient, times(1)).testStart(eq("test-name"), this.testContextCaptor.capture());
        Iterator<String> iterator = this.testContextCaptor.getValue().getTestExecutionTags().iterator();
        assertEquals("tag1", iterator.next());
        assertEquals("tag2", iterator.next());
    }

    @Test
    void onBeforeTest_noTagsArePassed_doesNotSetTagsinContext() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeTest("test-name");

        // Assert
        verify(this.reportiumClient, times(1)).testStart(eq("test-name"), this.testContextCaptor.capture());
        assertEquals(0, this.testContextCaptor.getValue().getTestExecutionTags().size());
    }

    @Test
    void onBeforeTest_nullIsPassedAsTags_doesNotSetTagsinContexts() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeTest("test-name", (String[]) null);

        // Assert
        verify(this.reportiumClient, times(1)).testStart(eq("test-name"), this.testContextCaptor.capture());
        assertEquals(0, this.testContextCaptor.getValue().getTestExecutionTags().size());
    }

    @Test
    void onBeforeTest_withoutPerfectoUrl_doesNotCallApi() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeTest("test-name", "tag1", "tag2");

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onBeforeTest_withPerfectoForced_setsTagsInContext() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        doReturn(true)
                .when(this.configuration)
                .getBoolean(PerfectoConfiguration.Keys.PERFECTO_FORCED, false);
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeTest("test-name", "tag1", "tag2");

        // Assert
        verify(this.reportiumClient, times(1)).testStart(eq("test-name"), this.testContextCaptor.capture());
        Iterator<String> iterator = this.testContextCaptor.getValue().getTestExecutionTags().iterator();
        assertEquals("tag1", iterator.next());
        assertEquals("tag2", iterator.next());
    }

    @Test
    void onSucceededTest_isCalled_marksTestAsSucceeded() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        when(this.reportiumClient.getReportUrl()).thenReturn("reportUrl");
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onSucceededTest();

        // Assert
        verify(this.reportiumClient, times(1)).testStop(this.testResultCaptor.capture());
        verify(this.log).info("Test Report URL: {}", "reportUrl");
        assertTrue(this.testResultCaptor.getValue().isSuccessful());
    }

    @Test
    void onSucceededTest_withoutPerfectoUrl_doesNotCallApi() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onSucceededTest();

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onSkippedTest_isCalled_marksTestAsFailed() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        when(this.reportiumClient.getReportUrl()).thenReturn("reportUrl");
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onSkippedTest("test-name");

        // Assert
        verify(this.reportiumClient, times(1)).testStop(this.testResultCaptor.capture());
        verify(this.log).info("Test Report URL: {}", "reportUrl");
        assertFalse(this.testResultCaptor.getValue().isSuccessful());
        assertEquals("Skipped", this.testResultCaptor.getValue().getMessage());
    }

    @Test
    void onSkippedTest_withoutPerfectoUrl_doesNotCallApi() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onSkippedTest("test-name");

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onFailedTest_isCalled_marksTestAsFailed() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        when(this.reportiumClient.getReportUrl()).thenReturn("reportUrl");
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        Exception exception = new Exception();

        // Act
        this.perfectoExtension.onFailedTest("error-message", exception);

        // Assert
        verify(this.reportiumClient, times(1)).testStop(this.testResultCaptor.capture());
        verify(this.log).info("Test Report URL: {}", "reportUrl");
        assertFalse(this.testResultCaptor.getValue().isSuccessful());
        assertEquals(0, this.testResultCaptor.getValue().getMessage().indexOf("error-message. Stack Trace:java.lang.Exception"));
    }

    @Test
    void onFailedTest_withoutPerfectoUrl_doesNotCallApi() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        Exception exception = new Exception();

        // Act
        this.perfectoExtension.onFailedTest("error-message", exception);

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onBeforeStep_isCalled_submitsStepName() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeStep("stepName");

        // Assert
        verify(this.reportiumClient, times(1)).stepStart("stepName");
    }

    @Test
    void onBeforeStep_withoutPerfectoUrl_doesNotCallApi() {
        // Arrange
        doReturn("http://test.not-perfecto.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Act
        this.perfectoExtension.onBeforeStep("stepName");

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onExecutionEvent_isCalled_doesNotDoAnything() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        Object testPayload = mock(Object.class);

        // Act
        this.perfectoExtension.onExecutionEvent("eventName", testPayload);

        // Assert
        verifyZeroInteractions(this.reportiumClient);
        verifyZeroInteractions(testPayload);
    }

    @Test
    void onDone_isCalled_doesNotDoAnything() {
        // Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;

        // Act
        this.perfectoExtension.onDone();

        // Assert
        verifyZeroInteractions(this.reportiumClient);
    }

    @Test
    void onGenerateCapabilities_tokenUserAndPasswordProvided_setsToken() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("test_user")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("test_pass")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("securityToken", "test_token");
        verify(this.mutableCapabilities, never()).setCapability(eq("user"), anyString());
        verify(this.mutableCapabilities, never()).setCapability(eq("password"), anyString());
    }

    @Test
    void onGenerateCapabilities_onlyTokenProvided_setsToken() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("securityToken", "test_token");
        verify(this.mutableCapabilities, never()).setCapability(eq("user"), anyString());
        verify(this.mutableCapabilities, never()).setCapability(eq("password"), anyString());
    }

    @Test
    void onGenerateCapabilities_emptyTokenValidUserAndPassword_setsUserAndPassword() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_user")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("test_pass")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration).getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, never()).setCapability(eq("securityToken"), anyString());
        verify(this.mutableCapabilities, times(1)).setCapability("user", "test_user");
        verify(this.mutableCapabilities, times(1)).setCapability("password", "test_pass");
    }

    @Test
    void onGenerateCapabilities_emptyTokenAndUser_throwsException() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("test_pass")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");

        //Act
        Executable action = () -> this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        AeonLaunchException exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("Please specify either a token or username and password for Perfecto.", exception.getMessage());
    }

    @Test
    void onGenerateCapabilities_emptyTokenAndPassword_throwsException() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_user")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");

        //Act
        Executable action = () -> this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        AeonLaunchException exception = assertThrows(AeonLaunchException.class, action);
        assertEquals("Please specify either a token or username and password for Perfecto.", exception.getMessage());
    }

    @Test
    void onGenerateCapabilities_noDescriptionProvided_doesNotSetDescriptionCapability() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        assertNull(this.mutableCapabilities.getCapability("description"));
    }

    @Test
    void onGenerateCapabilities_descriptionProvided_setsDescriptionCapability() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("description", "test-description");
    }

    @Test
    void onGenerateCapabilities_withTestName_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.adapter.getWebDriver()).thenReturn(this.androidDriver);
        when(this.reportiumClientFactory.createPerfectoReportiumClient(this.executionContextCaptor.capture())).thenReturn(this.reportiumClient);
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);
        this.perfectoExtension.onBeforeTest("Test Name 1");
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "Test Name 1");
    }

    @Test
    void onGenerateCapabilities_withSuiteName_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onBeforeStart("correlationId", "Test Name 1");
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "Test Name 1");
    }

    @Test
    void onGenerateCapabilities_withJobName_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");
        doReturn("Test Name 1")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.REPORT_JOB_NAME, "");

        //Act
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "Test Name 1");
    }

    @Test
    void onGenerateCapabilities_withJobNameAndCorrelationId_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");
        doReturn("Test Name 1")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.REPORT_JOB_NAME, "");

        //Act
        this.perfectoExtension.onBeforeStart("correlationId", null);
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "Test Name 1 correlationId");
    }

    @Test
    void onGenerateCapabilities_withCorrelationIdandNullJobName_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");

        //Act
        this.perfectoExtension.onBeforeStart("correlationId", null);
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "correlationId");
    }

    @Test
    void onGenerateCapabilities_withCorrelationIdAndEmptyJobName_setsScriptName() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn("test_token")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_TOKEN, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_USER, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.PERFECTO_PASS, "");
        doReturn("test-description")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.DEVICE_DESCRIPTION, "");
        doReturn("")
                .when(this.configuration)
                .getString(PerfectoConfiguration.Keys.REPORT_JOB_NAME, "");

        //Act
        this.perfectoExtension.onBeforeStart("correlationId", null);
        this.perfectoExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        //Assert
        verify(this.mutableCapabilities, times(1)).setCapability("scriptName", "correlationId");
    }

    @Test
    void onAfterLaunch_cleanEnvironmentRequested_cleansAppCache() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.aeonConfiguration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true)).thenReturn(true);
        when(this.aeonConfiguration.getString("aeon.appium.android.app_package", "")).thenReturn("test_appPackage");

        //Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.androidDriver);

        //Assert
        verify(this.log, times(1)).info("Cleaning application environment...");
        verify(this.androidDriver, times(1)).executeScript(eq("mobile:application:clean"), any());
        verify(this.androidDriver, times(1)).executeScript(eq("mobile:application:open"), any());
    }

    @Test
    void onAfterLaunch_scriptExecutionFails_quitsTheDriver() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        when(this.aeonConfiguration.getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true)).thenReturn(true);
        when(this.aeonConfiguration.getString("aeon.appium.android.app_package", "")).thenReturn("test_appPackage");
        when(this.androidDriver.executeScript(any(), any())).thenThrow(new ScriptExecutionException("error-message", new RuntimeException()));

        //Act
        Executable action = () -> this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.androidDriver);

        //Assert
        assertThrows(ScriptExecutionException.class, action);
        verify(this.log, times(1)).info("Cleaning application environment...");
        verify(this.androidDriver, times(1)).executeScript(eq("mobile:application:clean"), any());
        verify(this.androidDriver, times(1)).quit();
    }

    @Test
    void onAfterLaunch_cleanEnvironmentNotRequested_doesNotCleanAppCache() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn(false)
                .when(this.aeonConfiguration)
                .getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        doReturn("test_appPackage")
                .when(this.aeonConfiguration).getString("aeon.appium.android.app_package", "");

        //Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.androidDriver);

        //Assert
        verifyZeroInteractions(this.log);
        verifyZeroInteractions(this.androidDriver);
    }

    @Test
    void onAfterLaunch_appPackageNotSet_doesNotCleanAppCache() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn(true)
                .when(this.aeonConfiguration)
                .getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        doReturn("")
                .when(this.aeonConfiguration)
                .getString("aeon.appium.android.app_package", "");

        //Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, this.androidDriver);

        //Assert
        verifyZeroInteractions(this.log);
        verifyZeroInteractions(this.androidDriver);
    }

    @Test
    void onAfterLaunch_noAndroidDriverProvided_doesNotCleanAppCache() {
        //Arrange
        doReturn("http://test.perfectomobile.com/test/wd/hub")
                .when(this.configuration)
                .getString(SeleniumConfiguration.Keys.SELENIUM_GRID_URL, "");
        this.perfectoExtension = new PerfectoExtension(this.reportiumClientFactory, this.configuration);
        PerfectoExtension.log = this.log;
        doReturn(true)
                .when(this.aeonConfiguration)
                .getBoolean(SeleniumConfiguration.Keys.ENSURE_CLEAN_ENVIRONMENT, true);
        doReturn("test_appPackage")
                .when(this.aeonConfiguration)
                .getString("aeon.appium.android.app_package", "");
        IOSDriver iosDriver = mock(IOSDriver.class);

        //Act
        this.perfectoExtension.onAfterLaunch(this.aeonConfiguration, iosDriver);

        //Assert
        verifyZeroInteractions(this.log);
        verifyZeroInteractions(iosDriver);
    }
}
