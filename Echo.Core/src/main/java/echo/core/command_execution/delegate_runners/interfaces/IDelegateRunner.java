package echo.core.command_execution.delegate_runners.interfaces;

import echo.core.common.logging.ILog;
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
    void Execute(Consumer<IWebDriver> commandDelegate);

    /**
     * Executes a command.
     *
     * @param commandDelegate A command delegate.
     * @return An object.
     */
    Object Execute(Function<IWebDriver, Object> commandDelegate);
}
