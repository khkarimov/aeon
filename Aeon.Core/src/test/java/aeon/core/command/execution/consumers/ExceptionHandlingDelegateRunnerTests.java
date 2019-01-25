package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;
import aeon.core.common.exceptions.TimeoutExpiredException;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ExceptionHandlingDelegateRunnerTests {

    private ExceptionHandlingDelegateRunner exceptionHandlingDelegateRunner;

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
        //Arrange
        doThrow(TimeoutExpiredException.class).when(successor).execute(consumerCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(TimeoutExpiredException.class)).thenReturn(exceptionHandler);

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        ArgumentCaptor<TimeoutExpiredException> ac = ArgumentCaptor.forClass(TimeoutExpiredException.class);

        verify(exceptionHandlerFactory).createHandlerFor(TimeoutExpiredException.class);
        verify(exceptionHandler).handle(ac.capture());

        assertEquals(TimeoutExpiredException.class, ac.getValue().getClass());


    }

    @Test
    void execute_ConsumerRuntimeException_isCaught() {
        //Arrange
        doThrow(RuntimeException.class).when(successor).execute(consumerCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(RuntimeException.class)).thenReturn(exceptionHandler);

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        ArgumentCaptor<RuntimeException> ac = ArgumentCaptor.forClass(RuntimeException.class);

        verify(exceptionHandlerFactory).createHandlerFor(RuntimeException.class);
        verify(exceptionHandler).handle(ac.capture());

        assertEquals(RuntimeException.class, ac.getValue().getClass());
    }

    @Test
    void execute_ConsumerSuccessful_isCompleted() {
        //Arrange

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        verify(successor).execute(consumerCommandDelegate);
        verifyZeroInteractions(exceptionHandlerFactory);
    }

    @Test
    void execute_FunctionTimeoutExpired_isCaught() {
        //Arrange
        doThrow(TimeoutExpiredException.class).when(successor).execute(functionCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(TimeoutExpiredException.class)).thenReturn(exceptionHandler);

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        ArgumentCaptor<TimeoutExpiredException> ac = ArgumentCaptor.forClass(TimeoutExpiredException.class);

        verify(exceptionHandlerFactory).createHandlerFor(TimeoutExpiredException.class);
        verify(exceptionHandler).handle(ac.capture());

        assertEquals(TimeoutExpiredException.class, ac.getValue().getClass());
    }

    @Test
    void execute_FunctionRuntimeException_isCaught() {
        //Arrange
        doThrow(RuntimeException.class).when(successor).execute(functionCommandDelegate);
        when(exceptionHandlerFactory.createHandlerFor(RuntimeException.class)).thenReturn(exceptionHandler);

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        ArgumentCaptor<RuntimeException> ac = ArgumentCaptor.forClass(RuntimeException.class);

        verify(exceptionHandlerFactory).createHandlerFor(RuntimeException.class);
        verify(exceptionHandler).handle(ac.capture());

        assertEquals(RuntimeException.class, ac.getValue().getClass());
    }

    @Test
    void execute_FunctionSuccessful_isCompleted() {
        //Arrange

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        verify(successor).execute(functionCommandDelegate);
        verifyZeroInteractions(exceptionHandlerFactory);

    }
}
