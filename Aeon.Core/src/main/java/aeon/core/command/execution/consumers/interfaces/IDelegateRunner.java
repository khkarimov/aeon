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
     * Executes a command.
     *
     * @param commandDelegate A command delegate.
     * @return An object.
     */
    Object execute(Function<IDriver, Object> commandDelegate);
}
