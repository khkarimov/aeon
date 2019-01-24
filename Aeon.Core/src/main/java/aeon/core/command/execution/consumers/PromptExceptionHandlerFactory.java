package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;

/**
 * Class of Selenium Exception Handler Factory.
 */
public class PromptExceptionHandlerFactory implements IExceptionHandlerFactory {

    private boolean promptUserForContinueDecision;

    /**
     * Constructor for the selenium exception handler factory.
     *
     * @param promptUserForContinueDecision the initial value of the boolean.
     */
    public PromptExceptionHandlerFactory(boolean promptUserForContinueDecision) {
        this.promptUserForContinueDecision = promptUserForContinueDecision;
    }

    /**
     * Function that returns a boolean of the user's decision to continue.
     *
     * @return the decision of the user to continue as a boolean.
     */
    public final boolean getPromptUserForContinueDecision() {
        return promptUserForContinueDecision;
    }

    /**
     * Function creates a handler for the type of exception.
     *
     * @param typeOfexceptionToHandle the type of exception.
     * @return new instance of continue at user exception handler.
     */
    public final IExceptionHandler createHandlerFor(java.lang.Class typeOfexceptionToHandle) {
        if (getPromptUserForContinueDecision()) {
            return new ContinueAtUserExceptionHandler();
        }

        //// We could do something specific with Web exceptions:
        //// WebDriverException
        ////     ElementNotVisibleException
        ////     IllegalLocatorException
        ////     InvalidCookieDomainException
        ////     InvalidElementStateException
        ////     InvalidOperationException
        ////     InvalidSelectorException
        ////     NoAlertPresentException
        ////     NoSuchElementException
        ////     NoSuchFrameException
        ////     NoSuchWindowException
        ////     NotFoundException
        ////     StaleElementReferenceException
        ////     TimeoutException
        ////     UnableToSetCookieException
        ////     UnhandledAlertException
        //// Exception
        ////     NotImplementedException
        ////     NotSupportedException
        ////     UnexpectedTagNameException

        return new RethrowExceptionHandler();
    }
}
