package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GetScreenshotCommandTests {

    private GetScreenshotCommand getScreenshotCommand;

    @Mock
    private IDriver driver;

    @BeforeEach
    public void setUp() {
        getScreenshotCommand = new GetScreenshotCommand();
    }

    @Test
    public void commandDelegateGetSourceCommand() {
        //Arrange
        when(driver.getScreenshot()).thenReturn(new BufferedImage(1, 1, 1));

        //Act
        getScreenshotCommand.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).getScreenshot();
    }

    @Test
    public void testCommandDelegateNullDriver() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getScreenshotCommand.commandDelegate(null));
    }
}
