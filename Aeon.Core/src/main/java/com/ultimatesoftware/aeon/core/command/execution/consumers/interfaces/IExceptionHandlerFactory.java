package com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces;

/**
 * Interface for Exception Handler Factory.
 */
public interface IExceptionHandlerFactory {

    /**
     * Function creates a handle for the exception.
     *
     * @param typeOfExceptionToHandle the class type of exception to handle.
     * @return an IException Handler.
     */
    IExceptionHandler createHandlerFor(java.lang.Class typeOfExceptionToHandle);
}
