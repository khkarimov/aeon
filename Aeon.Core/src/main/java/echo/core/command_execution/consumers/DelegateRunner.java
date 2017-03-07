package echo.core.command_execution.consumers;

import echo.core.command_execution.consumers.interfaces.IDelegateRunner;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DelegateRunner implements IDelegateRunner {
    protected UUID guid;

    protected IDelegateRunner successor;

    protected DelegateRunner(UUID guid, IDelegateRunner successor) {
        this.guid = guid;
        this.successor = successor;
    }

    public final ILog getLog() {
        return successor.getLog();
    }

    public abstract void Execute(Consumer<IDriver> commandDelegate);

    public abstract Object Execute(Function<IDriver, Object> commandDelegate);
}
