package aeon.core.command.execution.consumers.interfaces;

public interface IExceptionHandler {

    /**
     * The handle function of runtime exceptions.
     * @param exceptionToHandle the exception to handle.
     */
    void handle(RuntimeException exceptionToHandle);
}
