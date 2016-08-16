package echo.core.command_execution.consumers;

import echo.core.command_execution.consumers.interfaces.IExceptionHandler;

public class ContinueAtUserExceptionHandler implements IExceptionHandler {
    public final void Handle(RuntimeException exceptionToHandle) {
        // TODO(DionnyS): JAVA_CONVERSION Show an option pane allowing the user to continue.
        throw exceptionToHandle;
    }
}
