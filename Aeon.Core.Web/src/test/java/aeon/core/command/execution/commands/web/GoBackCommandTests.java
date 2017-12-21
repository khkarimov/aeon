package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GoBackCommandTests {
    private GoBackCommand goBackCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown  = ExpectedException.none();

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;

    @Before
    public void setup(){goBackCommandObject = new GoBackCommand();}


    /**
     * Move back a single page in a browser's history
     * Usage:
     *  Context.browser.goBack();
     *
     * If no previous pages, it does nothing
     */
    @Test
    public void commandDelegateGoBackCommand(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);

        //Act
        Consumer<IDriver> action = goBackCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).goBack();
    }
}
