package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;

public class RethrowExceptionHandler implements IExceptionHandler {
    public final void handle(RuntimeException exceptionToHandle) {
        throw exceptionToHandle;
    }
}
