package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;
import com.ultimatesoftware.aeon.core.common.exceptions.TimeoutExpiredException;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The Exception Handling Delegate Runner class.
 */
public class ExceptionHandlingDelegateRunner extends DelegateRunner {

    private static Logger log = LoggerFactory.getLogger(ExceptionHandlingDelegateRunner.class);
    private IExceptionHandlerFactory exceptionHandlerFactory;

    /**
     * Constructor for the exception handling delegate runner.
     *
     * @param successor               the delegate runner to be set.
     * @param exceptionHandlerFactory the exception handler factory to be set.
     */
    public ExceptionHandlingDelegateRunner(IDelegateRunner successor, IExceptionHandlerFactory exceptionHandlerFactory) {
        super(successor);
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    /**
     * Function returns an ExceptionHandlerFactory.
     *
     * @return the exception handler factory.
     */
    public final IExceptionHandlerFactory getExceptionHandlerFactory() {
        return exceptionHandlerFactory;
    }

    @Override
    public void execute(Consumer<IDriver> commandDelegate) {
        executeDelegate(() -> successor.execute(commandDelegate));
    }

    @Override
    public Object execute(Function<IDriver, Object> commandDelegate) {
        return executeDelegateWithReturn(() -> successor.execute(commandDelegate));
    }

    private void executeDelegate(Runnable commandDelegateWrapper) {
        try {
            commandDelegateWrapper.run();
        } catch (TimeoutExpiredException e) {
            // Expects that TimeoutDelegateRunner is the first waiter decorating DelegateRunner and ExceptionHandlingDelegateRunner is after
            log.debug(e.getMessage());
            getExceptionHandlerFactory().createHandlerFor(TimeoutExpiredException.class).handle(e);
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
            getExceptionHandlerFactory().createHandlerFor(e.getClass()).handle(e);
        }
    }

    private Object executeDelegateWithReturn(Supplier<Object> commandDelegateWrapper) {
        try {
            return commandDelegateWrapper.get();
        } catch (TimeoutExpiredException e) {
            // Expects that TimeoutDelegateRunner is the first waiter decorating DelegateRunner and ExceptionHandlingDelegateRunner is after
            log.debug(e.getMessage());
            getExceptionHandlerFactory().createHandlerFor(TimeoutExpiredException.class).handle(e);
            return null;
        } catch (RuntimeException e) {
            log.debug(e.getMessage());
            getExceptionHandlerFactory().createHandlerFor(e.getClass()).handle(e);
            return null;
        }
    }
}
