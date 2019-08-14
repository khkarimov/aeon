package com.ultimatesoftware.aeon.core.extensions;

import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoggingTestExecutionExtensionTests {

    private LoggingTestExecutionExtension loggingTestExecutionExtension;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private ITestExecutionExtension testExecutionExtension1;

    @Mock
    private ITestExecutionExtension testExecutionExtension2;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);

        this.loggingTestExecutionExtension = new LoggingTestExecutionExtension();
        LoggingTestExecutionExtension.log = logger;
    }

    @Test
    void onStartUp_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onStartUp(this.configuration, "correlationId");

        // Assert
        verifyZeroInteractions(this.logger);
    }

    @Test
    void onBeforeStart_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onBeforeStart("correlationId", "suiteName");

        // Assert
        verifyZeroInteractions(this.logger);
    }

    @Test
    void onBeforeLaunch_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onBeforeLaunch(this.configuration);

        // Assert
        verifyZeroInteractions(this.logger);
    }

    @Test
    void onAfterLaunch_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onAfterLaunch(this.configuration, this.adapter);

        // Assert
        verifyZeroInteractions(this.logger);
    }

    @Test
    void onBeforeTest_isCalled_logsTestStartedEvent() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onBeforeTest("test name", "tag1", "tag2");

        // Assert
        verify(this.logger, times(1)).info("TEST STARTED: {}", "test name");
    }

    @Test
    void onSucceededTest_isCalled_logsTestSucceededEvent() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onSucceededTest();

        // Assert
        verify(this.logger, times(1)).info("TEST SUCCEEDED");
    }

    @Test
    void onSkippedTest_isCalled_logsTestSkippedEvent() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onSkippedTest("test name", "tag1", "tag2");

        // Assert
        verify(this.logger, times(1)).info("TEST SKIPPED: {}", "test name");
    }

    @Test
    void onFailedTest_isCalled_logsTestFailedEvent() {

        // Arrange
        Exception exception = new Exception("exception-message");

        // Act
        this.loggingTestExecutionExtension.onFailedTest("error-message", exception);

        // Assert
        verify(this.logger, times(1)).info("TEST FAILED: {}", "error-message");
    }

    @Test
    void onBeforeStep_isCalled_logsStepMessage() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onBeforeStep("step message");

        // Assert
        verify(this.logger, times(1)).info("step message");
    }

    @Test
    void onExecutionEvent_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onExecutionEvent("event name", new Object());

        // Assert
        verifyZeroInteractions(this.logger);
    }

    @Test
    void onDone_isCalled_doesNothing() {

        // Arrange

        // Act
        this.loggingTestExecutionExtension.onDone();

        // Assert
        verifyZeroInteractions(this.logger);
    }
}
