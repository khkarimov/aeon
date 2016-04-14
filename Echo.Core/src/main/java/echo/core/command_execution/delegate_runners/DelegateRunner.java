package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DelegateRunner implements IDelegateRunner {
    protected UUID guid;

    private IDelegateRunner successor;

    protected DelegateRunner(UUID guid, IDelegateRunner successor) {
        this.guid = guid;
        this.successor = successor;
    }

    public final ILog getLog() {
        return getSuccessor().getLog();
    }

    public final IDelegateRunner getSuccessor() {
        return successor;
    }

    public abstract void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate);

    public abstract Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate);
}