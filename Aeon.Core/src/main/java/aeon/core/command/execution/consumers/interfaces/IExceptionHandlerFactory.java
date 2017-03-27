package aeon.core.command.execution.consumers.interfaces;

public interface IExceptionHandlerFactory {
    boolean getPromptUserForContinueDecision();

    IExceptionHandler createHandlerFor(java.lang.Class typeOfExceptionToHandle);
}
