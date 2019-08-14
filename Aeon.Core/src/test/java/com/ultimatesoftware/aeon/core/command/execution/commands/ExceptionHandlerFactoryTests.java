package com.ultimatesoftware.aeon.core.command.execution.commands;

import com.ultimatesoftware.aeon.core.command.execution.consumers.ExceptionHandlerFactory;
import com.ultimatesoftware.aeon.core.command.execution.consumers.RethrowExceptionHandler;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionHandlerFactoryTests {

    @Test
    void createHandlerFor_false_returnRethrowExceptionHandler() {
        //Arrange
        ExceptionHandlerFactory exceptionHandlerFactory = new ExceptionHandlerFactory();

        //Act
        IExceptionHandler handler = exceptionHandlerFactory.createHandlerFor(RuntimeException.class);

        //Assert
        assertTrue(handler instanceof RethrowExceptionHandler);
    }
}
