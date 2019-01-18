package aeon.core.command.execution.consumers.interfaces;

import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A delegate runner.
 */
public interface IDelegateRunner {

    /**
     * Executes a command.
     *
     * @param commandDelegate A command delegate.
     */
    void execute(Consumer<IDriver> commandDelegate);

    /**
     * Executes a command with return value.
     *
     * @param commandDelegate A command delegate with return value.
     * @return The return value.
     */
    Object execute(Function<IDriver, Object> commandDelegate);
}
