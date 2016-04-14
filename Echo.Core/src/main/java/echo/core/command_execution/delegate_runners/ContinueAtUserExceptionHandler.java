package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IExceptionHandler;

public class ContinueAtUserExceptionHandler implements IExceptionHandler {
    public final void Handle(RuntimeException exceptionToHandle) {
        // TODO: JAVA_CONVERSION Show an option pane allowing the user to continue.
        throw exceptionToHandle;
    }
}
