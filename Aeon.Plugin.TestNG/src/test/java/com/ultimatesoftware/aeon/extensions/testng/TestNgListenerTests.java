package com.ultimatesoftware.aeon.extensions.testng;

import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TestNgListenerTests {

    private TestNgListener testNgListener;

    @Mock
    private ITestResult testResult;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private ITestExecutionExtension testExecutionExtension1;

    @Mock
    private ITestExecutionExtension testExecutionExtension2;

    @BeforeEach
    void setup() {
        testNgListener = new TestNgListener();
        Aeon.setPluginManager(this.pluginManager);
    }

    @Test
    void onTestStart_isCalled_triggersOnBeforeTestEvent() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        when(this.testResult.getInstanceName()).thenReturn("class_name");
        when(this.testResult.getName()).thenReturn("test_name");

        // Act
        this.testNgListener.onTestStart(this.testResult);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onBeforeTest("class_name.test_name", (String[]) null);
        verify(this.testExecutionExtension2, times(1)).onBeforeTest("class_name.test_name", (String[]) null);
    }

    @Test
    void onTestSuccess_isCalled_triggersTestOnSucceededTestEvent() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));

        // Act
        this.testNgListener.onTestSuccess(this.testResult);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSucceededTest();
        verify(this.testExecutionExtension2, times(1)).onSucceededTest();
    }

    @Test
    void onTestFailure_called_triggersOnFailedTestEvent() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Exception exception = new Exception("exception-message");
        when(this.testResult.getThrowable()).thenReturn(exception);

        // Act
        this.testNgListener.onTestFailure(this.testResult);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("exception-message", exception);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("exception-message", exception);
    }

    @Test
    void onTestSkipped_isCalled_triggersOnSkippedTestEvent() {

        // Arrange
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        when(this.testResult.getInstanceName()).thenReturn("class_name");
        when(this.testResult.getName()).thenReturn("test_name");

        // Act
        this.testNgListener.onTestSkipped(this.testResult);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSkippedTest("class_name.test_name", (String[]) null);
        verify(this.testExecutionExtension2, times(1)).onSkippedTest("class_name.test_name", (String[]) null);
    }

    @Test
    void onTestFailedButWithinSuccessPercentage_isCalled_doesNothing() {

        // Arrange

        // Act
        this.testNgListener.onTestFailedButWithinSuccessPercentage(this.testResult);

        // Assert
        verifyZeroInteractions(this.testExecutionExtension1);
        verifyZeroInteractions(this.testExecutionExtension2);
    }

    @Test
    void onStart_isCalledWithTestContext_doesNothing() {

        // Arrange

        // Act
        this.testNgListener.onStart((ITestContext) null);

        // Assert
        verifyZeroInteractions(this.testExecutionExtension1);
        verifyZeroInteractions(this.testExecutionExtension2);
    }

    @Test
    void onFinish_isCalledWithTestContext_doesNothing() {

        // Arrange

        // Act
        this.testNgListener.onFinish((ITestContext) null);

        // Assert
        verifyZeroInteractions(this.testExecutionExtension1);
        verifyZeroInteractions(this.testExecutionExtension2);
    }
}
