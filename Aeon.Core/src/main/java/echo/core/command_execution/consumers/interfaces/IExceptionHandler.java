package echo.core.command_execution.consumers.interfaces;

public interface IExceptionHandler {
    void Handle(RuntimeException exceptionToHandle);
}
