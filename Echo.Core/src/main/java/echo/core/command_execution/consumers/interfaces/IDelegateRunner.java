package echo.core.command_execution.consumers.interfaces;

import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IWebDriver;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A delegate runner.
 */
public interface IDelegateRunner {
    /**
     * Gets a log.
     */
    ILog getLog();

    /**
     * Executes a command.
     *
     * @param commandDelegate A command delegate.
     */
    void Execute(Consumer<IDriver> commandDelegate);

    /**
     * Executes a command.
     *
     * @param commandDelegate A command delegate.
     * @return An object.
     */
    Object Execute(Function<IDriver, Object> commandDelegate);
}
