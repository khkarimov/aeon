package aeon.core.command.execution.commands.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class NotExistsCommandTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    WebControl control;

    @Mock
    IByWeb selector;

    @Mock
    IWebDriver driver;

    private NotExistsCommand command;

    @Before
    public void setUp()
    {
        command = new NotExistsCommand(selector);
    }

    @Test
    public void notExists_FindElementSuccessfulTry()
    {
        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).findElement(selector);
    }

    @Test
    public void notExists_FindElementCatch()
    {
        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        doThrow(new NoSuchElementException()).when(driver).findElement(selector);
    }

    @Test
    public void notExists_CallsExecute()
    {
        //Arrange
        when(driver.findElement(selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).notExists(control);
    }
}
