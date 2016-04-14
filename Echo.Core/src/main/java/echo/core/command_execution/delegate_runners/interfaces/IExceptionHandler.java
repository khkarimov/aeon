package echo.core.command_execution.delegate_runners.interfaces;

public interface IExceptionHandler {
    void Handle(RuntimeException exceptionToHandle);
}