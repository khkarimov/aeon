package echo.core.command_execution.delegate_runners;

import echo.core.command_execution.delegate_runners.interfaces.IExceptionHandler;
import echo.core.command_execution.delegate_runners.interfaces.IExceptionHandlerFactory;

public class SeleniumExceptionHandlerFactory implements IExceptionHandlerFactory {
    private boolean promptUserForContinueDecision;

    public SeleniumExceptionHandlerFactory(boolean promptUserForContinueDecision) {
        this.promptUserForContinueDecision = promptUserForContinueDecision;
    }

    public final boolean getPromptUserForContinueDecision() {
        return promptUserForContinueDecision;
    }

    public final IExceptionHandler CreateHandlerFor(java.lang.Class typeOfexceptionToHandle) {
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
