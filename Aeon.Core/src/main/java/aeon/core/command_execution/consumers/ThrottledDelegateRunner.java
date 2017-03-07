package aeon.core.command_execution.consumers;

import aeon.core.command_execution.consumers.interfaces.IDelegateRunner;
import aeon.core.common.helpers.Sleep;
import aeon.core.framework_abstraction.drivers.IDriver;
import org.joda.time.Duration;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThrottledDelegateRunner extends DelegateRunner {
    private Duration throttleFactor;

    public ThrottledDelegateRunner(UUID guid, IDelegateRunner successor, Duration throttleFactor) {
        super(guid, successor);
        this.throttleFactor = throttleFactor;
    }

    @Override
    public void Execute(Consumer<IDriver> commandDelegate) {
        Sleep.WaitDuration(throttleFactor);
        successor.Execute(commandDelegate);
    }

    @Override
    public Object Execute(Function<IDriver, Object> commandDelegate) {
        Sleep.WaitDuration(throttleFactor);
        return successor.Execute(commandDelegate);
    }
}
