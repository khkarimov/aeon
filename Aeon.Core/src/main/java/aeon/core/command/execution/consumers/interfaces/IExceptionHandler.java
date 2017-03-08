package aeon.core.command.execution.consumers.interfaces;

public interface IExceptionHandler {
    void Handle(RuntimeException exceptionToHandle);
}
