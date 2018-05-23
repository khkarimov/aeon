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
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WindowDoesNotExistByTitleCommandTests {

    private WindowDoesNotExistByTitleCommand windowDoesNotExistByTitleCommand;

    private String windowTitle = "WindowTitle";

    @Mock
    private IWebDriver driver;


    @BeforeEach
    public void setup(){windowDoesNotExistByTitleCommand = new WindowDoesNotExistByTitleCommand(windowTitle);}

    @Test
    public void IllegalArgumentExceptionDriverNull(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> windowDoesNotExistByTitleCommand.commandDelegate(null));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
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
