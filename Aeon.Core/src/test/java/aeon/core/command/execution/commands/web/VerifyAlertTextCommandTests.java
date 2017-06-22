package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VerifyAlertTextCommandTests {

    private VerifyAlertTextCommand verifyAlertTextCommandObject;
    private String compareText = "Compare Test";

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
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

    @Test(expected = IllegalArgumentException.class)
    public void driverDelegateVerifyAlertTextCommandWithNullDriver(){
        // Arrange
        // nothing to do

        // Act
        verifyAlertTextCommandObject.driverDelegate(null);
    }
}