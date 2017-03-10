package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DelegateRunner implements IDelegateRunner {

    protected IDelegateRunner successor;

    protected DelegateRunner(IDelegateRunner successor) {
        this.successor = successor;
    }

    public abstract void Execute(Consumer<IDriver> commandDelegate);

    public abstract Object Execute(Function<IDriver, Object> commandDelegate);
}
