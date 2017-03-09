package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDelegateRunner implements IDelegateRunner {
    private IDriver driver;
    private static Logger log = LogManager.getLogger(CommandDelegateRunner.class);

    public CommandDelegateRunner(IDriver driver) {
        this.driver = driver;
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
