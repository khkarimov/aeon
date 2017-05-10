package aeon.core.command.execution.consumers.interfaces;

public interface IExceptionHandler {

    void handle(RuntimeException exceptionToHandle);
}
