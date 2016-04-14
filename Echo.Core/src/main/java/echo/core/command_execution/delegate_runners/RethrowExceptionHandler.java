package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IExceptionHandler;

public class RethrowExceptionHandler implements IExceptionHandler {
    public final void Handle(RuntimeException exceptionToHandle) {
        throw exceptionToHandle;
    }
}
