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

public class GoForwardCommandTests {

    private GoForwardCommand goForwardCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;


    /* SETUP */

    @Before
    public void setup() {
        goForwardCommandObject = new GoForwardCommand();
    }


    /* TEST CASES */

    /**
     * Move forward a single entry in the browser's history.
     * Usage:
     *       Context.browser.goForward();
     *
     * Does nothing if we are on the latest page viewed.
     */





    @Test
    public void commandDelegateGoForwardCommand() {
        //Arrange
        when(initializer.setContext()).thenReturn(action); //when context set, perform action


        //Act
        Consumer<IDriver> action = goForwardCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).goForward();
    }


}