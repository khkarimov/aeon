package com.ultimatesoftware.aeon.core.command.execution.consumers;

import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandlerFactory;

/**
 * Class of Selenium Exception Handler Factory.
 */
public class ExceptionHandlerFactory implements IExceptionHandlerFactory {

    /**
     * Function creates a handler for the type of exception.
     *
     * @param typeOfExceptionToHandle the type of exception.
     * @return new instance of continue at user exception handler.
     */
    public final IExceptionHandler createHandlerFor(java.lang.Class typeOfExceptionToHandle) {

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
