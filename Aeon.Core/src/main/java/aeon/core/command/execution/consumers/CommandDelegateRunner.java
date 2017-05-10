package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDelegateRunner implements IDelegateRunner {

    private IDriver driver;

    public CommandDelegateRunner(IDriver driver) {
        this.driver = driver;
    }

    public final void execute(Consumer<IDriver> commandDelegate) {
        if (commandDelegate == null) {
            return;
        }

        commandDelegate.accept(driver);
    }

    public final Object execute(Function<IDriver, Object> commandDelegate) {
        return commandDelegate == null ? null : commandDelegate.apply(driver);
    }
}
