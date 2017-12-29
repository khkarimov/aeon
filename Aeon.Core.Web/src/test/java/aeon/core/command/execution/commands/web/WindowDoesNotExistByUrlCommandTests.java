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

public class WindowDoesNotExistByUrlCommandTests {

    private WindowDoesNotExistByUrlCommand windowDoesNotExistByUrlCommand;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();

    private String url = "Url";

    @Mock
    private IWebDriver driver;


    @Before
    public void setup(){windowDoesNotExistByUrlCommand = new WindowDoesNotExistByUrlCommand(url);}

    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentExceptionDriverNull(){
        //Arrange

        // Act
        windowDoesNotExistByUrlCommand.commandDelegate(null);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test
    public void commandDelegateWindowDoesNotExistByTitle(){
        //Arrange

        // Act
        windowDoesNotExistByUrlCommand.commandDelegate(driver);

        // Assert
        verify(driver, times(1)).windowDoesNotExistByUrl(url);
    }
}
