package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;
import aeon.core.common.exceptions.TimeoutExpiredException;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
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
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private IExceptionHandlerFactory exceptionHandlerFactory;
    @Mock
    private Consumer<IDriver> consumerCommandDelegate;
    @Mock
    private Function<IDriver, Object> functionCommandDelegate;

    @BeforeEach
    void setup() {
        exceptionHandlingDelegateRunner = new ExceptionHandlingDelegateRunner(successor, exceptionHandlerFactory);
    }

    @Test
    void execute_ConsumerTimeoutExpired_isCaught() {
        //Arrange
        doThrow(TimeoutExpiredException.class).when(successor).execute(consumerCommandDelegate);

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        ArgumentCaptor<java.lang.Class> ac = ArgumentCaptor.forClass(java.lang.Class.class);
        verify(exceptionHandlerFactory).createHandlerFor(ac.capture());
        assertEquals(TimeoutExpiredException.class, ac.getValue());
    }

    @Test
    void execute_ConsumerRuntimeException_isCaught() {
        //Arrange
        doThrow(RuntimeException.class).when(successor).execute(consumerCommandDelegate);

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        ArgumentCaptor<java.lang.Class> ac = ArgumentCaptor.forClass(java.lang.Class.class);
        verify(exceptionHandlerFactory).createHandlerFor(ac.capture());
        assertEquals(RuntimeException.class, ac.getValue());
    }

    @Test
    void execute_ConsumerSuccessful_isCompleted() {
        //Arrange

        //Act
        exceptionHandlingDelegateRunner.execute(consumerCommandDelegate);

        //Assert
        verifyZeroInteractions(exceptionHandlerFactory);
    }

    @Test
    void execute_FunctionTimeoutExpired_isCaught() {
        //Arrange
        doThrow(TimeoutExpiredException.class).when(successor).execute(functionCommandDelegate);

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        ArgumentCaptor<java.lang.Class> ac = ArgumentCaptor.forClass(java.lang.Class.class);
        verify(exceptionHandlerFactory).createHandlerFor(ac.capture());
        assertEquals(TimeoutExpiredException.class, ac.getValue());
    }

    @Test
    void execute_FunctionRuntimeException_isCaught() {
        //Arrange
        doThrow(RuntimeException.class).when(successor).execute(functionCommandDelegate);

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        ArgumentCaptor<java.lang.Class> ac = ArgumentCaptor.forClass(java.lang.Class.class);
        verify(exceptionHandlerFactory).createHandlerFor(ac.capture());
        assertEquals(RuntimeException.class, ac.getValue());

    }

    @Test
    void execute_FunctionSuccessful_isCompleted() {
        //Arrange

        //Act
        exceptionHandlingDelegateRunner.execute(functionCommandDelegate);

        //Assert
        verifyZeroInteractions(exceptionHandlerFactory);
    }
}
