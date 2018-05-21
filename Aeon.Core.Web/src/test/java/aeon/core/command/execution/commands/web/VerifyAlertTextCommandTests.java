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
public class VerifyAlertTextCommandTests {

    private VerifyAlertTextCommand verifyAlertTextCommandObject;
    private String compareText = "Compare Test";

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup() {
        verifyAlertTextCommandObject = new VerifyAlertTextCommand(compareText);
    }

    @Test
    public void driverDelegateVerifyAlertTextCommand(){
        // Arrange
        // nothing to do

        // Act
        verifyAlertTextCommandObject.driverDelegate(driver);

        // Assert
        verify(driver, times(1)).verifyAlertText(compareText);
    }

    @Test
    public void driverDelegateVerifyAlertTextCommandWithNullDriver(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () ->verifyAlertTextCommandObject.driverDelegate(null));
    }
}