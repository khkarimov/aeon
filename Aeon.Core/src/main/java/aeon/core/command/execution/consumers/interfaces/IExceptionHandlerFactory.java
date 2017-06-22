package aeon.core.command.execution.consumers.interfaces;

/**
 * Interface for Exception Handler Factory.
 */
public interface IExceptionHandlerFactory {

    /**
     * Gets the user decision to continue.
     * @return a boolean of the user decision to continue.
     */
    boolean getPromptUserForContinueDecision();

    /**
     * Function creates a handle for the exception.
     * @param typeOfExceptionToHandle the class type of exception to handle.
     * @return an Iexception Handler.
     */
    IExceptionHandler createHandlerFor(java.lang.Class typeOfExceptionToHandle);
}
