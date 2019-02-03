package aeon.core.command.execution.consumers;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TimeoutDelegateRunnerTests {

    private TimeoutDelegateRunner timeoutDelegateRunner;

    @Mock
    private IDelegateRunner successor;
    @Mock
    private IDriver driver;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AutomationInfo automationInfo;
    @Mock
    private Consumer<IDriver> consumerCommandDelegate;
    @Mock
    private Function<IDriver, Object> functionCommandDelegate;

    @BeforeEach
    void setUp() {
        Duration duration = Duration.ofMillis(100);
        timeoutDelegateRunner = new TimeoutDelegateRunner(successor, driver, duration, automationInfo);
    }

    @Test
    void execute_Consumer_completesCorrectly() {

        // Arrange

        // Act
        timeoutDelegateRunner.execute(consumerCommandDelegate);

        // Assert
        verify(successor, times(1)).execute(consumerCommandDelegate);
        verify(driver, times(0)).getScreenshot();
        verify(automationInfo, times(0)).screenshotTaken(any());
    }

    @Test
    void execute_Function_completesCorrectly() {

        // Arrange

        // Act
        timeoutDelegateRunner.execute(functionCommandDelegate);

        // Assert
        verify(successor, times(1)).execute(functionCommandDelegate);
        verify(driver, times(0)).getScreenshot();
        verify(automationInfo, times(0)).screenshotTaken(any());
    }

    @Test
    void execute_commandFunctionRuntimeException_isThrown() {

        // Arrange
        doThrow(RuntimeException.class).when(successor).execute(functionCommandDelegate);
        when(automationInfo.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)).thenReturn(false);

        // Act
        Executable executable = () -> timeoutDelegateRunner.execute(functionCommandDelegate);

        // Assert
        assertThrows(RuntimeException.class, executable);
        verify(driver, times(1)).getScreenshot();
        verify(automationInfo, times(1)).screenshotTaken(any());
    }

    @Test
    void execute_commandConsumerRuntimeException_isThrown() {

        // Arrange
        doThrow(RuntimeException.class).when(successor).execute(consumerCommandDelegate);
        when(automationInfo.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)).thenReturn(false);

        // Act
        Executable executable = () -> timeoutDelegateRunner.execute(consumerCommandDelegate);

        // Assert
        assertThrows(RuntimeException.class, executable);
        verify(driver, times(1)).getScreenshot();
        verify(automationInfo, times(1)).screenshotTaken(any());
    }

    @Test
    void execute_automationInfoReporting_testFailedLogged() {

        // Arrange
        doThrow(new RuntimeException("Check error is thrown")).when(successor).execute(consumerCommandDelegate);
        when(automationInfo.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)).thenReturn(true);

        // Act
        Executable executable = () -> timeoutDelegateRunner.execute(consumerCommandDelegate);

        // Assert
        assertThrows(RuntimeException.class, executable);
        verify(automationInfo, times(1)).testFailed(anyString(), any(RuntimeException.class));
    }
}
