package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IWebDriver;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDelegateRunner implements IDelegateRunner {
    private IWebDriver driver;
    private ILog log;

    public CommandDelegateRunner(IWebDriver driver, ILog log) {
        this.driver = driver;
        this.log = log;
    }

    public final ILog getLog() {
        return log;
    }

    public final void Execute(Consumer<IWebDriver> commandDelegate) {
        if (commandDelegate == null) {
            return;
        }

        commandDelegate.accept(driver);
    }

    public final Object Execute(Function<IWebDriver, Object> commandDelegate) {
        return commandDelegate == null ? null : commandDelegate.apply(driver);
    }
}