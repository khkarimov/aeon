package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DismissAlertCommandTests {
    private DismissAlertCommand alertCommand;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        alertCommand = new DismissAlertCommand();
    }

    @Test
    public void dismissAlert_executeDismissAlert() {
        // Arrange

        // Act
        alertCommand.getCommandDelegate().accept(driver);

        // Assert
        verify(driver, times(1)).dismissAlert();
    }
}
