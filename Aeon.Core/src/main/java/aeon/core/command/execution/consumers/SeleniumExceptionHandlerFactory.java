package aeon.core.command.execution.consumers;

import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;

public class SeleniumExceptionHandlerFactory implements IExceptionHandlerFactory {
    private boolean promptUserForContinueDecision;

    public SeleniumExceptionHandlerFactory(boolean promptUserForContinueDecision) {
        this.promptUserForContinueDecision = promptUserForContinueDecision;
    }

    public final boolean getPromptUserForContinueDecision() {
        return promptUserForContinueDecision;
    }

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
