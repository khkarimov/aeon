package com.ultimatesoftware.aeon.core.command.execution.commands;

import com.ultimatesoftware.aeon.core.command.execution.consumers.ContinueAtUserExceptionHandler;
import com.ultimatesoftware.aeon.core.command.execution.consumers.PromptExceptionHandlerFactory;
import com.ultimatesoftware.aeon.core.command.execution.consumers.RethrowExceptionHandler;
import com.ultimatesoftware.aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PromptExceptionHandlerFactoryUnitTests {
    public Class testException;
    IExceptionHandler handler;

    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    public void getPromptUserForContinueDecision_true_returnTrue(boolean testPromptUserForContinueDecision) {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(testPromptUserForContinueDecision);

        //Act
        boolean checkPrompt = testPromptExceptionHandlerFactory.getPromptUserForContinueDecision();

        //Assert
        assertEquals(testPromptUserForContinueDecision, checkPrompt);
    }

    @Test
    public void createHandlerFor_true_returnContinueAtUserExceptionHandler() {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(true);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertTrue(handler instanceof ContinueAtUserExceptionHandler);
    }

    @Test
    public void createHandlerFor_false_returnRethrowExceptionHandler() {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(false);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertTrue(handler instanceof RethrowExceptionHandler);
    }

}
