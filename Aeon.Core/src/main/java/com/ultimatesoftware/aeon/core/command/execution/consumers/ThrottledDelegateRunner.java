package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class for Throttle delegate runner.
 */
public class ThrottledDelegateRunner extends DelegateRunner {

    private Duration throttleFactor;

    /**
     * The constructor for the Throttled Delegate Runner class.
     * @param successor the delegate runner.
     * @param throttleFactor the duration.
     */
    public ThrottledDelegateRunner(IDelegateRunner successor, Duration throttleFactor) {
        super(successor);
        this.throttleFactor = throttleFactor;
    }

    @Override
    public void execute(Consumer<IDriver> commandDelegate) {
        Sleep.wait(throttleFactor);
        successor.execute(commandDelegate);
    }

    @Override
    public Object execute(Function<IDriver, Object> commandDelegate) {
        Sleep.wait(throttleFactor);
        return successor.execute(commandDelegate);
    }
}
