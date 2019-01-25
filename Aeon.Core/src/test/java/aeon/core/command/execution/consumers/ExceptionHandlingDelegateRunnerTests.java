package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;
import aeon.core.common.exceptions.TimeoutExpiredException;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ExceptionHandlingDelegateRunnerTests {

    private ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner;
    private RuntimeException runtimeException;
    private TimeoutExpiredException timeoutException;

    @Mock
    private IDelegateRunner successor;
    @Mock
    private IExceptionHandlerFactory exceptionHandlerFactory;
    @Mock
    private Consumer<IDriver> consumerCommandDelegate;
    @Mock
    private Function<IDriver, Object> functionCommandDelegate;
    @Mock
    private IExceptionHandler exceptionHandler;

    @BeforeEach
    void setup() {
        exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(successor, exceptionHandlerFactory);
    }

    @Test
    void execute_ConsumerTimeoutExpired_isCaught() {
        // Arrange
        timeoutException = new TimeoutExpiredException("Test", Duration.ZERO);
        doThrow(timeoutException).when(successor).execute(consumerCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(TimeoutExpiredException.class)).thenReturn(exceptionHandler);
        // Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);
        // Assert
        verify(exceptionHandlerFactory, times(1)).createHandlerFor(TimeoutExpiredException.class);
        verify(exceptionHandler, times(1)).handle(timeoutException);
    }

    @Test
    void execute_ConsumerRuntimeException_isCaught() {
        // Arrange
        runtimeException = new RuntimeException();
        doThrow(runtimeException).when(successor).execute(consumerCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(RuntimeException.class)).thenReturn(exceptionHandler);
        // Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);
        // Assert
        verify(exceptionHandlerFactory, times(1)).createHandlerFor(RuntimeException.class);
        verify(exceptionHandler, times(1)).handle(runtimeException);
    }

    @Test
    void execute_ConsumerSuccessful_isCompleted() {
        // Arrange
        // Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);
        // Assert
        verify(successor, times(1)).execute(consumerCommandDelegate);
        verifyZeroInteractions(exceptionHandlerFactory);
    }

    @Test
    void execute_FunctionTimeoutExpired_isCaught() {
        // Arrange
        timeoutException = new TimeoutExpiredException("Test", Duration.ZERO);
        doThrow(timeoutException).when(successor).execute(functionCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(TimeoutExpiredException.class)).thenReturn(exceptionHandler);
        // Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);
        // Assert
        verify(exceptionHandlerFactory, times(1)).createHandlerFor(TimeoutExpiredException.class);
        verify(exceptionHandler, times(1)).handle(timeoutException);
    }

    @Test
    void execute_FunctionRuntimeException_isCaught() {
        // Arrange
        runtimeException = new RuntimeException();
        doThrow(runtimeException).when(successor).execute(functionCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(RuntimeException.class)).thenReturn(exceptionHandler);
        // Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);
        // Assert
        verify(exceptionHandlerFactory, times(1)).createHandlerFor(RuntimeException.class);
        verify(exceptionHandler, times(1)).handle(runtimeException);
    }

    @Test
    void execute_FunctionSuccessful_isCompleted() {
        // Arrange
        // Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);
        // Assert
        verify(successor, times(1)).execute(functionCommandDelegate);
        verifyZeroInteractions(exceptionHandlerFactory);
    }
}
