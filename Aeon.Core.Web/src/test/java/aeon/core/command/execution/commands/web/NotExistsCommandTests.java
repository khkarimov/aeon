package aeon.core.command.execution.commands.web;

import aeon.core.common.exceptions.NoSuchElementException;
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
        // Arrange

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).findElement(selector);
    }

    @Test
    public void notExists_FindElementCatch()
    {
        // Arrange
        NoSuchElementException e = new NoSuchElementException(new Exception(), selector);
        when(driver.findElement(selector)).thenThrow(e);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times( 1)).findElement(selector);
        verify(driver, times( 0)).notExists(control);
    }

    @Test
    public void notExists_CallsExecute()
    {
        // Arrange
        when(driver.findElement(selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).notExists(control);
    }
}
