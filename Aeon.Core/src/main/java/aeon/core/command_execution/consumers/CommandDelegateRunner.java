package aeon.core.command_execution.consumers;

import aeon.core.command_execution.consumers.interfaces.IDelegateRunner;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDelegateRunner implements IDelegateRunner {
    private IDriver driver;
    private ILog log;

    public CommandDelegateRunner(IDriver driver, ILog log) {
        this.driver = driver;
        this.log = log;
    }

    public final ILog getLog() {
        return log;
    }

    public final void Execute(Consumer<IDriver> commandDelegate) {
        if (commandDelegate == null) {
            return;
        }

        commandDelegate.accept(driver);
    }

    public final Object Execute(Function<IDriver, Object> commandDelegate) {
        return commandDelegate == null ? null : commandDelegate.apply(driver);
    }
}
