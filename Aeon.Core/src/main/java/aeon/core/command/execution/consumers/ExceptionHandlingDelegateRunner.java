package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;
import aeon.core.common.exceptions.TimeoutExpiredException;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionHandlingDelegateRunner extends DelegateRunner {
    private IExceptionHandlerFactory exceptionHandlerFactory;
    private static Logger log = LogManager.getLogger(ExceptionHandlingDelegateRunner.class);

    public ExceptionHandlingDelegateRunner(IDelegateRunner successor, IExceptionHandlerFactory exceptionHandlerFactory) {
        super(successor);
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    public final IExceptionHandlerFactory getExceptionHandlerFactory() {
        return exceptionHandlerFactory;
    }

    @Override
    public void Execute(Consumer<IDriver> commandDelegate) {
        ExecuteDelegate(() -> successor.Execute(commandDelegate));
    }

    @Override
    public Object Execute(Function<IDriver, Object> commandDelegate) {
        return ExecuteDelegateWithReturn(() -> successor.Execute(commandDelegate));
    }

    private void ExecuteDelegate(Runnable commandDelegateWrapper) {
        try {
            commandDelegateWrapper.run();
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (TimeoutExpiredException e) {
            // Expects that TimeoutDelegateRunner is the first waiter decorating DelegateRunner and ExceptionHandlingDelegateRunner is after
            log.debug(e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(TimeoutExpiredException.class).Handle(e);
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(e.getClass()).Handle(e);
        }
    }

    private Object ExecuteDelegateWithReturn(Supplier<Object> commandDelegateWrapper) {
        try {
            return commandDelegateWrapper.get();
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (TimeoutExpiredException e) {
            // Expects that TimeoutDelegateRunner is the first waiter decorating DelegateRunner and ExceptionHandlingDelegateRunner is after
            log.debug(e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(TimeoutExpiredException.class).Handle(e);
            return null;
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(e.getClass()).Handle(e);
            return null;
        }
    }
}
