package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;
import org.slf4j.Logger;

import java.awt.*;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonTestExecutionTests {

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

    @Captor
    private ArgumentCaptor<String> correlationId;

    @BeforeEach
    void setUp() {
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);
        AeonTestExecution.log = logger;
    }

    @Test
    void startUp_isCalled_generatesCorrelationIdAndTriggersOnStartUpEvent() {

        // Arrange
        // Reset correlation ID
        AeonTestExecution.done();

        // Act
        AeonTestExecution.startUp(this.configuration);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        verify(this.testExecutionExtension2, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        UUID uuid = UUID.fromString(this.correlationId.getValue());
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(0));
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(1));
    }

    @Test
    void startUp_isCalledWithCorrelationIdSet_doesNotGenerateCorrelationIdAndTriggersOnStartUpEvent() {

        // Arrange
        // Generate and retrieve correlation ID
        AeonTestExecution.beforeStart();
        verify(this.testExecutionExtension1, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        verify(this.testExecutionExtension2, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        UUID uuid = UUID.fromString(this.correlationId.getValue());

        // Act
        AeonTestExecution.startUp(this.configuration);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        verify(this.testExecutionExtension2, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(0));
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(1));
    }

    @Test
    void beforeLaunch_isCalled_triggersOnBeforeLaunchEvent() {

        // Arrange

        // Act
        AeonTestExecution.beforeLaunch(this.configuration);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeLaunch(this.configuration);
        verify(this.testExecutionExtension2, times(1)).onBeforeLaunch(this.configuration);
    }

    @Test
    void beforeStart_isCalledWithNoSuiteName_generatesCorrelationIdAndTriggersOnBeforeStartEvent() {

        // Arrange

        // Act
        AeonTestExecution.beforeStart();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        verify(this.testExecutionExtension2, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        UUID uuid = UUID.fromString(this.correlationId.getValue());
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(0));
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(1));
    }

    @Test
    void beforeStart_isCalledWithSuiteName_generatesCorrelationIdAndTriggersOnBeforeStartEvent() {

        // Arrange

        // Act
        AeonTestExecution.beforeStart("suiteName");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStart(this.correlationId.capture(), eq("suiteName"));
        verify(this.testExecutionExtension2, times(1)).onBeforeStart(this.correlationId.capture(), eq("suiteName"));
        UUID uuid = UUID.fromString(this.correlationId.getValue());
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(0));
        assertEquals(uuid.toString(), this.correlationId.getAllValues().get(1));
    }

    @Test
    void done_isCalled_resetsCorrelationIdAndTriggersOnDoneEvent() {

        // Arrange
        // Generate and retrieve correlation ID
        AeonTestExecution.beforeStart();
        verify(this.testExecutionExtension1, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        verify(this.testExecutionExtension2, times(1)).onBeforeStart(this.correlationId.capture(), eq(null));
        UUID uuid = UUID.fromString(this.correlationId.getValue());

        // Act
        AeonTestExecution.done();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onDone();
        verify(this.testExecutionExtension2, times(1)).onDone();

        // Verify correlation ID was reset by checking whether "startUp" creates a new one
        AeonTestExecution.startUp(this.configuration);
        verify(this.testExecutionExtension1, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        verify(this.testExecutionExtension2, times(1)).onStartUp(eq(this.configuration), this.correlationId.capture());
        assertNotEquals(uuid.toString(), this.correlationId.getAllValues().get(2));
        assertNotEquals(uuid.toString(), this.correlationId.getAllValues().get(3));
    }

    @Test
    void launched_whenCalled_triggersOnAfterLaunchEvent() {

        // Arrange

        // Act
        AeonTestExecution.launched(this.configuration, this.adapter);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onAfterLaunch(this.configuration, this.adapter);
        verify(this.testExecutionExtension2, times(1)).onAfterLaunch(this.configuration, this.adapter);
    }

    @Test
    void launched_whenOnAfterLaunchEventFails_logsWarning() {

        // Arrange
        Exception exception = new RuntimeException("error-message");
        doThrow(exception).when(this.testExecutionExtension2).onAfterLaunch(this.configuration, this.adapter);

        // Act
        AeonTestExecution.launched(this.configuration, this.adapter);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onAfterLaunch(this.configuration, this.adapter);
        verify(this.testExecutionExtension2, times(1)).onAfterLaunch(this.configuration, this.adapter);
        verify(this.logger, times(1)).warn("error-message", exception);
    }

    @Test
    void testSucceeded_isCalled_triggersTestOnSucceededTestEvent() {

        // Arrange

        // Act
        AeonTestExecution.testSucceeded();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSucceededTest();
        verify(this.testExecutionExtension2, times(1)).onSucceededTest();
    }

    @Test
    void testFailed_calledWithNoException_triggersOnFailedTestEvent() {

        // Arrange

        // Act
        AeonTestExecution.testFailed("error-message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("error-message", null);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("error-message", null);
    }

    @Test
    void testFailed_calledWithException_triggersOnFailedTestEvent() {

        // Arrange
        Exception exception = new Exception("exception-message");

        // Act
        AeonTestExecution.testFailed("error-message", exception);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("error-message", exception);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("error-message", exception);
    }

    @Test
    void executionEvent_isCalled_triggersOnExecutionEventEvent() {

        // Arrange
        Image image = mock(Image.class);

        // Act
        AeonTestExecution.executionEvent("screenshotTaken", image);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onExecutionEvent("screenshotTaken", image);
        verify(this.testExecutionExtension2, times(1)).onExecutionEvent("screenshotTaken", image);
    }

    @Test
    void startTest_isCalled_triggersOnBeforeTestEvent() {

        // Arrange

        // Act
        AeonTestExecution.startTest("test name");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeTest("test name", (String[]) null);
        verify(this.testExecutionExtension2, times(1)).onBeforeTest("test name", (String[]) null);
    }

    @Test
    void startTest_isCalledWithTags_triggersOnBeforeTestEvent() {

        // Arrange

        // Act
        AeonTestExecution.startTest("test name", "tag1", "tag2");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeTest("test name", "tag1", "tag2");
        verify(this.testExecutionExtension2, times(1)).onBeforeTest("test name", "tag1", "tag2");
    }

    @Test
    void testSkipped_isCalledWithTags_triggersOnSkippedTestEvent() {

        // Arrange

        // Act
        AeonTestExecution.testSkipped("test name", "tag1", "tag2");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSkippedTest("test name", "tag1", "tag2");
        verify(this.testExecutionExtension2, times(1)).onSkippedTest("test name", "tag1", "tag2");
    }

    @Test
    void given_isCalled_triggersOnBeforeStepEventWithGivenPrefix() {

        // Arrange

        // Act
        AeonTestExecution.given("message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStep("GIVEN message");
        verify(this.testExecutionExtension2, times(1)).onBeforeStep("GIVEN message");
    }

    @Test
    void given_isCalled_triggersOnBeforeStepEventWithWhenPrefix() {

        // Arrange

        // Act
        AeonTestExecution.when("message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStep("WHEN message");
        verify(this.testExecutionExtension2, times(1)).onBeforeStep("WHEN message");
    }

    @Test
    void given_isCalled_triggersOnBeforeStepEventWithThenPrefix() {

        // Arrange

        // Act
        AeonTestExecution.then("message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStep("THEN message");
        verify(this.testExecutionExtension2, times(1)).onBeforeStep("THEN message");
    }

    @Test
    void given_isCalled_triggersOnBeforeStepEventWithAndPrefix() {

        // Arrange

        // Act
        AeonTestExecution.and("message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeStep("AND message");
        verify(this.testExecutionExtension2, times(1)).onBeforeStep("AND message");
    }
}
