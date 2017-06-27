package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.common.helpers.Sleep;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.joda.time.Duration;

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
        Sleep.waitDuration(throttleFactor);
        successor.execute(commandDelegate);
    }

    @Override
    public Object execute(Function<IDriver, Object> commandDelegate) {
        Sleep.waitDuration(throttleFactor);
        return successor.execute(commandDelegate);
    }
}
