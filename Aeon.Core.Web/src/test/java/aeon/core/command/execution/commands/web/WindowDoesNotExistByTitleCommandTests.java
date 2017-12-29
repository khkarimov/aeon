package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WindowDoesNotExistByTitleCommandTests {

    private WindowDoesNotExistByTitleCommand windowDoesNotExistByTitleCommand;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();

    private String windowTitle = "WindowTitle";

    @Mock
    private IWebDriver driver;


    @Before
    public void setup(){windowDoesNotExistByTitleCommand = new WindowDoesNotExistByTitleCommand(windowTitle);}

    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentExceptionDriverNull(){
        //Arrange

        // Act
        windowDoesNotExistByTitleCommand.commandDelegate(null);

        // Assert
        thrown.expectMessage("driver");

    }

    @Test
    public void commandDelegateWindowDoesNotExistByTitle(){
        //Arrange

        // Act
        windowDoesNotExistByTitleCommand.commandDelegate(driver);

        // Assert
        verify(driver, times(1)).windowDoesNotExistByTitle(windowTitle);
    }
}
