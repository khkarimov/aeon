package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WindowDoesNotExistByUrlCommandTests {

    private WindowDoesNotExistByUrlCommand windowDoesNotExistByUrlCommand;

    private String url = "Url";

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup(){windowDoesNotExistByUrlCommand = new WindowDoesNotExistByUrlCommand(url);}

    @Test
    public void IllegalArgumentExceptionDriverNull(){
        //Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> windowDoesNotExistByUrlCommand.commandDelegate(null));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
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
