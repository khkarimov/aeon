package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;

/**
 * Class for rethrowing exception handler.
 */
public class RethrowExceptionHandler implements IExceptionHandler {

    /**
     * Function to handle the runtime exceptions.
     * @param exceptionToHandle the exception to handle.
     */
    public final void handle(RuntimeException exceptionToHandle) {
        throw exceptionToHandle;
    }
}
