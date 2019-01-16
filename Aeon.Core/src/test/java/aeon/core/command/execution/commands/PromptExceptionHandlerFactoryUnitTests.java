package aeon.core.command.execution.commands;

import aeon.core.command.execution.consumers.ContinueAtUserExceptionHandler;
import aeon.core.command.execution.consumers.PromptExceptionHandlerFactory;
import aeon.core.command.execution.consumers.RethrowExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;


public class PromptExceptionHandlerFactoryUnitTests {
    public Class testException;
    IExceptionHandler handler;

    @Test
    public void getPromptUserForContinueDecision_true_returnTrue() {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(true);

        //Act
        boolean prompt = testPromptExceptionHandlerFactory.getPromptUserForContinueDecision();

        //Assert
        assertTrue(prompt);
    }

    @Test
    public void createHandlerFor_true_returnContinueAtUserExceptionHandler() {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(true);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertThat(handler, instanceOf(ContinueAtUserExceptionHandler.class));
    }

    @Test
    public void createHandlerFor_false_returnRethrowExceptionHandler() {
        //Arrange
        PromptExceptionHandlerFactory testPromptExceptionHandlerFactory = new PromptExceptionHandlerFactory(false);

        //Act
        handler = testPromptExceptionHandlerFactory.createHandlerFor(testException);

        //Assert
        assertThat(handler, instanceOf(RethrowExceptionHandler.class));
    }

}
