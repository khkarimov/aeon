/**
 * Created by.
 */
package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The commandDelegateRunner class.
 */
public class CommandDelegateRunner implements IDelegateRunner {

    private IDriver driver;

    /**
     * Constructor for Command Delegate Runner, instantiated with a driver.
     * @param driver The web driver.
     */
    public CommandDelegateRunner(IDriver driver) {
        this.driver = driver;
    }

    /**
     * Execute command of a command delegate.
     * @param commandDelegate A command delegate.
     */
    public final void execute(Consumer<IDriver> commandDelegate) {
        if (commandDelegate == null) {
            return;
        }

        commandDelegate.accept(driver);
    }

    /**
     * Function to execute a commandDelegate.
     * @param commandDelegate A command delegate.
     * @return if commandDelegate is null, return null, else the commandDelegate with the applied driver.
     */
    public final Object execute(Function<IDriver, Object> commandDelegate) {
        return commandDelegate == null ? null : commandDelegate.apply(driver);
    }
}
