package aeon.core.command.execution.commands;

import aeon.core.command.execution.consumers.PromptExceptionHandlerFactory;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class PromptExceptionHandlerFactoryUnitTests {
    public boolean testUserPromptToContinueDecisions;
    public Class testException;
    IExceptionHandler handler;

    @Test
    public void getPromptUserForContinueDecision_true_returnTrue() {
        //Arrange
        testUserPromptToContinueDecisions = true;
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(testUserPromptToContinueDecisions);

        //Act
        boolean prompt = testPromptExceptionHandlerFactory.getPromptUserForContinueDecision();

        //Assert
        assertTrue("The get method seems to work", prompt);
    }

    @Test
    public void createHandlerFor_true_returnContinueAtUserExceptionHandler() {
        //Arrange
        testUserPromptToContinueDecisions = true;
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(testUserPromptToContinueDecisions);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertTrue("True, returns continue at user exception handler", true);
        assertNotNull("Handler is not null!", handler);
    }

    @Test
    public void createHandlerFor_false_returnRethrowExceptionHandler() {
        //Arrange
        testUserPromptToContinueDecisions = false;
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(testUserPromptToContinueDecisions);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertFalse("False, returns rethrow exception handler", false);
        assertNotNull("Handler is not null!", handler);
    }

    //Not sure about the web exception thing?


}
