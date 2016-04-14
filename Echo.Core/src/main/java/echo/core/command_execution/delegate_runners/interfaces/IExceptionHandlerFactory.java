package echo.core.command_execution.delegate_runners.interfaces;

public interface IExceptionHandlerFactory {
    boolean getPromptUserForContinueDecision();

    IExceptionHandler CreateHandlerFor(java.lang.Class typeOfExceptionToHandle);
}
