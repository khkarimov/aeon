/**
 * Package Info.
 */
package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Abstract class for Delegate Runner.
 */
public abstract class DelegateRunner implements IDelegateRunner {

    protected IDelegateRunner successor;

    /**
     * Constructor for Delegate Runner class.
     * @param successor sets the successor for the Delegate Runner.
     */
    protected DelegateRunner(IDelegateRunner successor) {
        this.successor = successor;
    }

    /**
     * Abstract for execute command.
     * @param commandDelegate A command delegate.
     */
    public abstract void execute(Consumer<IDriver> commandDelegate);

    /**
     * Function executes on a command Delegate.
     * @param commandDelegate A command delegate.
     * @return the Object.
     */
    public abstract Object execute(Function<IDriver, Object> commandDelegate);
}
