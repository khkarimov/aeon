package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandler;

/**
 * Class to handle continuing at a user exception.
 */
public class ContinueAtUserExceptionHandler implements IExceptionHandler {

    /**
     * Function to handle an exception.
     * @param exceptionToHandle the exception to handle.
     */
    public final void handle(RuntimeException exceptionToHandle) {
        // TODO(DionnyS): JAVA_CONVERSION Show an option pane allowing the user to continue.
        throw exceptionToHandle;
    }
}
