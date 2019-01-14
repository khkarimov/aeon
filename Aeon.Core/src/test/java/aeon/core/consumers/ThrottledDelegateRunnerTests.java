package aeon.core.consumers;

import aeon.core.command.execution.consumers.ThrottledDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)

public class ThrottledDelegateRunnerTests {

    private ThrottledDelegateRunner throttledDelegateRunner;
    private IDelegateRunner delegateRunner = mock(IDelegateRunner.class);

    @BeforeEach
    void setup() {
        throttledDelegateRunner = new ThrottledDelegateRunner(delegateRunner, Duration.ofSeconds(1));
    }

    @Test
    public void voidExecute_CallsExecute_ShouldBeCalled() {

        // Arrange
        Consumer commandDelegate = mock(Consumer.class);

        // Act
        throttledDelegateRunner.execute(commandDelegate);

        // Assert
        verify(delegateRunner, times(1)).execute(commandDelegate);
    }

    @Test
    public void objectExecute_CallsExecute_ShouldBeCalled() {

        // Arrange
        Function<IDriver, Object> commandDelegate1 = mock(Function.class);
        when(throttledDelegateRunner.execute(any(Function.class))).
                thenReturn(new Object());

        // Act
        boolean objectInstan = throttledDelegateRunner.execute(commandDelegate1) instanceof Object;

        // Assert
        verify(delegateRunner, times(1)).execute(commandDelegate1);

        assertTrue(objectInstan);

    }
}




