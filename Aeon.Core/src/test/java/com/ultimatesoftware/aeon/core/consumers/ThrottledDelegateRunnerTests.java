package com.ultimatesoftware.aeon.core.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.ThrottledDelegateRunner;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)

public class ThrottledDelegateRunnerTests {

    private ThrottledDelegateRunner throttledDelegateRunner;

    @Mock
    private IDelegateRunner delegateRunner;

    @Mock
    private Consumer commandDelegate;

    @Mock
    Function<IDriver, Object> commandDelegateFunction;

    @BeforeEach
    void setup() {
        throttledDelegateRunner = new ThrottledDelegateRunner(delegateRunner, Duration.ofSeconds(0));
    }

    @Test
    public void voidExecute_CallsExecute_ShouldBeCalled() {

        // Arrange

        // Act
        throttledDelegateRunner.execute(commandDelegate);

        // Assert
        verify(delegateRunner, times(1)).execute(commandDelegate);
    }

    @Test
    public void objectExecute_CallsExecute_ShouldBeCalled() {

        //Arrange

        // Act
        throttledDelegateRunner.execute(commandDelegateFunction);

        // Assert
        verify(delegateRunner, times(1)).execute(commandDelegateFunction);

    }
}
