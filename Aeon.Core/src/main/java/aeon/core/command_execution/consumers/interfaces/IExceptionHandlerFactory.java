package aeon.core.command_execution.consumers.interfaces;

public interface IExceptionHandlerFactory {
    boolean getPromptUserForContinueDecision();

    IExceptionHandler CreateHandlerFor(java.lang.Class typeOfExceptionToHandle);
}
