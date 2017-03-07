package aeon.core.command_execution.consumers;

import aeon.core.command_execution.consumers.interfaces.IExceptionHandler;

public class RethrowExceptionHandler implements IExceptionHandler {
    public final void Handle(RuntimeException exceptionToHandle) {
        throw exceptionToHandle;
    }
}
