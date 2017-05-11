package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;

public class ContinueAtUserExceptionHandler implements IExceptionHandler {

    public final void handle(RuntimeException exceptionToHandle) {
        // TODO(DionnyS): JAVA_CONVERSION Show an option pane allowing the user to continue.
        throw exceptionToHandle;
    }
}
