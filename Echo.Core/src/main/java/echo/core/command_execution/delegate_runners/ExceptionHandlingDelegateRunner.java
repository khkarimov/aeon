package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IDelegateRunner;
import echo.core.command_execution.delegate_runners.interfaces.IExceptionHandlerFactory;
import echo.core.common.exceptions.TimeoutExpiredException;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionHandlingDelegateRunner extends DelegateRunner {
    private IExceptionHandlerFactory exceptionHandlerFactory;

    public ExceptionHandlingDelegateRunner(UUID guid, IDelegateRunner successor, IExceptionHandlerFactory exceptionHandlerFactory) {
        super(guid, successor);
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    public final IExceptionHandlerFactory getExceptionHandlerFactory() {
        return exceptionHandlerFactory;
    }

    @Override
    public void Execute(Consumer<IFrameworkAbstractionFacade> commandDelegate) {
        ExecuteDelegate(() -> successor.Execute(commandDelegate));
    }

    @Override
    public Object Execute(Function<IFrameworkAbstractionFacade, Object> commandDelegate) {
        return ExecuteDelegateWithReturn(() -> successor.Execute(commandDelegate));
    }

    private void ExecuteDelegate(Runnable commandDelegateWrapper) {
        try {
            commandDelegateWrapper.run();
        } catch (OutOfMemoryError e) {
            throw e;
        } catch (TimeoutExpiredException e) {
            // Expects that TimeoutDelegateRunner is the first waiter decorating DelegateRunner and ExceptionHandlingDelegateRunner is after
            getLog().Debug(guid, e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(TimeoutExpiredException.class).Handle(e);
        } catch (RuntimeException e) {
            getLog().Debug(guid, e.getMessage());
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
            getLog().Debug(guid, e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(TimeoutExpiredException.class).Handle(e);
            return null;
        } catch (RuntimeException e) {
            getLog().Debug(guid, e.getMessage());
            getExceptionHandlerFactory().CreateHandlerFor(e.getClass()).Handle(e);
            return null;
        }
    }
}