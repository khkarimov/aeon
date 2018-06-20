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
public class SwitchToMainWindowCommandTests {
    private SwitchToMainWindowCommand switchToMainWindowCommand;
    private String mainWindow = "";
    private boolean check = true;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        switchToMainWindowCommand = new SwitchToMainWindowCommand(mainWindow, check);
    }

    @Test
    public void commandDelegateSwitchToMainWindowCommand() {
        //Arrange

        //Act
        switchToMainWindowCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).switchToMainWindow(mainWindow, check);
    }

    @Test
    public void driverNullThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> switchToMainWindowCommand.driverDelegate(null));
    }
}
