package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.logging.ILog;

import java.util.function.Consumer;
import java.util.function.Function;

public class CommandDelegateRunner implements IDelegateRunner {
    private IFrameworkAbstractionFacade frameworkAbstractionFacade;
    private ILog log;

    public CommandDelegateRunner(IFrameworkAbstractionFacade frameworkAbstractionFacade, ILog log) {
        this.frameworkAbstractionFacade = frameworkAbstractionFacade;
        this.log = log;
    }

    public final ILog getLog() {
        return log;
    }

    public final void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate) {
        if (commandDelegate == null) {
            return;
        }

        commandDelegate.accept(frameworkAbstractionFacade);
    }

    public final Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate) {
        return commandDelegate == null ? null : commandDelegate.apply(frameworkAbstractionFacade);
    }
}