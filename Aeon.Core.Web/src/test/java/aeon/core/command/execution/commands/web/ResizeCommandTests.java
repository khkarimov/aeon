package aeon.core.command.execution.commands.web;

import aeon.core.common.web.BrowserSize;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ResizeCommandTests {
    private ResizeCommand resizeCommand;
    private Dimension dimensionSize;
    private BrowserSize browserSize;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        resizeCommand = new ResizeCommand(browserSize);
    }

    @Test
    public void commandDelegateResizeCommand() {
        //Arrange

        //Act
        resizeCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).resize(dimensionSize);
    }

    @Test
    public void driverNullThrowsException(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> resizeCommand.driverDelegate(null));
    }
}
