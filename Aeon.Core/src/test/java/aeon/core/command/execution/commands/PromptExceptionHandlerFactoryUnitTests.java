package aeon.core.command.execution.commands;

import aeon.core.command.execution.consumers.ContinueAtUserExceptionHandler;
import aeon.core.command.execution.consumers.PromptExceptionHandlerFactory;
import aeon.core.command.execution.consumers.RethrowExceptionHandler;
import aeon.core.command.execution.consumers.interfaces.IExceptionHandler;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;


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
        assertTrue(checkPrompt == testPromptUserForContinueDecision);
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
