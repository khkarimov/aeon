package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.image.BufferedImage;
import java.util.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ReportingTestExecutionExtensionTests {

    private ReportingTestExecutionExtension reportingTestExecutionExtension;

    @Mock
    private ReportController reportController;

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private IAdapter adapter;

    @Captor
    private ArgumentCaptor<Report> reportCaptor;

    private java.awt.image.BufferedImage image = new BufferedImage(2, 2, TYPE_INT_RGB);

    @BeforeEach
    void setup() {
        this.reportingTestExecutionExtension = new ReportingTestExecutionExtension(this.reportController, this.configuration);
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = ReportingTestExecutionExtension.createInstance();

        // Assert
        assertEquals(ReportingTestExecutionExtension.class, extension.getClass());
    }

    @Test
    void onBeforeStart_reportExists_initializesNewReport() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId1", "suiteName1");

        // Act
        this.reportingTestExecutionExtension.onBeforeStart("correlationId2", "suiteName2");
        this.reportingTestExecutionExtension.onDone();

        // Assert
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("correlationId2", this.reportCaptor.getValue().getCorrelationId());
        assertEquals("suiteName2", this.reportCaptor.getValue().getName());
        assertNotEquals(0, this.reportCaptor.getValue().getTimer().getEndTime());
    }

    @Test
    void onStartUp_reportExists_doesNotInitializeNewReport() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId1", "suiteName1");

        // Act
        this.reportingTestExecutionExtension.onStartUp(this.aeonConfiguration, "correlationId2");
        this.reportingTestExecutionExtension.onDone();

        // Assert
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("correlationId2", this.reportCaptor.getValue().getCorrelationId());
        assertEquals("suiteName1", this.reportCaptor.getValue().getName());
        assertNotEquals(0, this.reportCaptor.getValue().getTimer().getEndTime());
    }

    @Test
    void onBeforeLaunch_doesNothing() {

        // Arrange

        // Act
        this.reportingTestExecutionExtension.onBeforeLaunch(this.aeonConfiguration);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onAfterLaunch_doesNothing() {

        // Arrange

        // Act
        this.reportingTestExecutionExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.adapter);
    }

    @Test
    void onBeforeTest_hasOnlyTestNameAndDisplayClassNameIsSetToFalse_createsNewTestCaseWithOnlyDescription() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(false);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("testName");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
    }

    @Test
    void onBeforeTest_hasOnlyTestNameAndDisplayClassNameIsSetToTrue_createsNewTestCaseWithOnlyDescription() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("testName");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
    }

    @Test
    void onBeforeTest_hasTestNameAndClassNameAndDisplayClassNameIsSetToTrue_createsNewTestCaseWithTestNameAndClassName() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
    }

    @Test
    void onBeforeTest_previousTestExisted_createsNewTestCase() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(1).getPrefix());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(1).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(1).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(1).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(1).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(1).getStatus());
    }

    @Test
    void onSkippedTest_hasTestNameAndClassNameAndDisplayClassNameIsSetToTrue_createsNewTestCaseWithTestNameAndClassName() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onSkippedTest("className.testName");

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("disabled", this.reportCaptor.getValue().getSequence().get(0).getStatus());
    }

    @Test
    void onFailedTest_doesNotHaveThrowable_setsOnlyErrorMessage() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");
        this.reportingTestExecutionExtension.onFailedTest("error-message", null);

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("failed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("error-message", this.reportCaptor.getValue().getSequence().get(0).getFailedExpectations().get(0).getMessage());
        assertNull(this.reportCaptor.getValue().getSequence().get(0).getFailedExpectations().get(0).getStack());
    }

    @Test
    void onFailedTest_hasThrowable_setsErrorMessageAndStackTrace() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");
        this.reportingTestExecutionExtension.onFailedTest("error-message", new Exception("exception-message"));

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("failed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("error-message", this.reportCaptor.getValue().getSequence().get(0).getFailedExpectations().get(0).getMessage());
        assertEquals(0, this.reportCaptor.getValue().getSequence().get(0).getFailedExpectations().get(0).getStack().indexOf("java.lang.Exception: exception-message"));
    }

    @Test
    void onBeforeStep_ifNotCompleted_addsHighLevelStep() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");

        // Act
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step");

        // Assert
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(0).getName());
        assertEquals("high-level-step", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(1).getName());
    }

    @Test
    void onBeforeStep_ifCompleted_doesNotaddHighLevelStep() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Act
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step");

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(0).getName());
        assertEquals(1, this.reportCaptor.getValue().getSequence().get(0).getSteps().size());
    }

    @Test
    void onBeforeStep_testDoesNotExistYet_createsNewTestCaseWithIncompleteDataButDoesNotFail() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");

        // Act
        this.reportingTestExecutionExtension.onBeforeStep("message");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
    }

    @Test
    void onExecutionEvent_ifScreenshotTaken_addsScreenshotToCurrentTest() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");

        // Act
        this.reportingTestExecutionExtension.onExecutionEvent("screenshotTaken", this.image);

        // Assert
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAIAAAD91JpzAAAAC0lEQVR42mNgQAYAAA4AATo1BFYAAAAASUVORK5CYII=", this.reportCaptor.getValue().getSequence().get(0).getScreenshotPath());
    }

    @Test
    void onExecutionEvent_ifScreenshotIsNull_DoesNotAddScreenshotToTest() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        this.reportingTestExecutionExtension.onBeforeTest("className.testName");

        // Act
        this.reportingTestExecutionExtension.onExecutionEvent("screenshotTaken", null);

        // Assert
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("className", this.reportCaptor.getValue().getSequence().get(0).getPrefix());
        assertEquals("testName", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNotEquals(0, this.reportCaptor.getValue().getSequence().get(0).getStartTime());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStarted());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getStopped());
        assertNotEquals("", this.reportCaptor.getValue().getSequence().get(0).getDuration());
        assertEquals("passed", this.reportCaptor.getValue().getSequence().get(0).getStatus());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getScreenshotPath());
    }

    @Test
    void onExecutionEvent_severalCommandInitializedEvent_addsStepsToCorrectTestsAndHighLevelSteps() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "step-before");
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step-1");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command1");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command2");
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step-2");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command3");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command4");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "step-should-not-get-recorded");
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "step-before2");
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step-3");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command5");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command6");
        this.reportingTestExecutionExtension.onBeforeStep("high-level-step-4");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command7");
        this.reportingTestExecutionExtension.onExecutionEvent("commandInitialized", "command8");
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(0).getName());
        assertEquals("step-before", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(0).getSteps().get(0));
        assertEquals("high-level-step-1", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(1).getName());
        assertEquals("command1", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(1).getSteps().get(0));
        assertEquals("command2", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(1).getSteps().get(1));
        assertEquals("high-level-step-2", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(2).getName());
        assertEquals("command3", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(2).getSteps().get(0));
        assertEquals("command4", this.reportCaptor.getValue().getSequence().get(0).getSteps().get(2).getSteps().get(1));
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(0).getName());
        assertEquals("step-before2", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(0).getSteps().get(0));
        assertEquals("high-level-step-3", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(1).getName());
        assertEquals("command5", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(1).getSteps().get(0));
        assertEquals("command6", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(1).getSteps().get(1));
        assertEquals("high-level-step-4", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(2).getName());
        assertEquals("command7", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(2).getSteps().get(0));
        assertEquals("command8", this.reportCaptor.getValue().getSequence().get(1).getSteps().get(2).getSteps().get(1));
    }

    @Test
    void onExecutionEvent_browserLogsCollectedEvent_addsBrowserLogsToTestsWhichDoNotAlreadyHaveBrowserLogs() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);
        List<Map<String, Object>> browserLogs1 = new ArrayList<>();
        List<Map<String, Object>> browserLogs2 = Collections.singletonList(new HashMap<>());

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onExecutionEvent("browserLogsCollected", browserLogs1);
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName3");
        this.reportingTestExecutionExtension.onExecutionEvent("browserLogsCollected", browserLogs2);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals(browserLogs1, this.reportCaptor.getValue().getSequence().get(0).getBrowserLogs());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals(browserLogs2, this.reportCaptor.getValue().getSequence().get(1).getBrowserLogs());
        assertEquals("testName3", this.reportCaptor.getValue().getSequence().get(2).getDescription());
        assertEquals(browserLogs2, this.reportCaptor.getValue().getSequence().get(2).getBrowserLogs());
    }

    @Test
    void onExecutionEvent_browserLogsCollectedEventButLogsAreNull_doesNotAddBrowserLogsToTests() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onExecutionEvent("browserLogsCollected", null);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertNull(this.reportCaptor.getValue().getSequence().get(0).getBrowserLogs());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertNull(this.reportCaptor.getValue().getSequence().get(1).getBrowserLogs());
    }

    @Test
    void onUploadSucceeded_videoUploaded_addsVideoToTests() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onUploadSucceeded("video-url", "video", null);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals("video-url", this.reportCaptor.getValue().getSequence().get(0).getVideoUrl());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals("video-url", this.reportCaptor.getValue().getSequence().get(1).getVideoUrl());
    }

    @Test
    void onUploadSucceeded_videoUploadedAndTestAlreadyHasVideo_addsVideoToTestsWhichDoNotAlreadyHaveVideo() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onUploadSucceeded("video-url", "video", null);
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onUploadSucceeded("video-url2", "video", null);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals("video-url", this.reportCaptor.getValue().getSequence().get(0).getVideoUrl());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals("video-url2", this.reportCaptor.getValue().getSequence().get(1).getVideoUrl());
    }

    @Test
    void onUploadSucceeded_videoUploadedButUrlIsNull_doesNotAddVideoToTests() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onUploadSucceeded(null, "video", null);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getVideoUrl());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(1).getVideoUrl());
    }

    @Test
    void onUploadSucceeded_somethingElseUploaded_doesNotaddVideoToTests() {

        // Arrange
        this.reportingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");
        when(this.configuration.getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true)).thenReturn(true);

        // Act
        this.reportingTestExecutionExtension.onBeforeTest("className.testName1");
        this.reportingTestExecutionExtension.onSucceededTest();
        this.reportingTestExecutionExtension.onBeforeTest("className.testName2");
        this.reportingTestExecutionExtension.onUploadSucceeded("report-url", "report", null);
        this.reportingTestExecutionExtension.onSucceededTest();

        // Assert
        this.reportingTestExecutionExtension.onDone();
        verify(this.reportController, times(1)).writeReportsAndUpload(this.reportCaptor.capture());
        assertEquals("testName1", this.reportCaptor.getValue().getSequence().get(0).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(0).getVideoUrl());
        assertEquals("testName2", this.reportCaptor.getValue().getSequence().get(1).getDescription());
        assertEquals("", this.reportCaptor.getValue().getSequence().get(1).getVideoUrl());
    }
}
