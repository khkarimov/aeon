package aeon.core.command.execution.commands.web;


import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetElementAttributeCommandTests {

    private GetElementAttributeCommand getElementAttributeCommand;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private  IWebDriver driver;
    @Mock
    private WebControl control;

    private String attributeName = "Name";

    @Before
    public void setup() { getElementAttributeCommand = new GetElementAttributeCommand(selector, initializer, attributeName); }

    @Test(expected = IllegalArgumentException.class)
    public void DriverNullThrowsException(){
        // Arrange
        
        // Act
        getElementAttributeCommand.commandDelegateOverride(null, control);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ControlNullThrowsException(){
        // Arrange

        // Act
        getElementAttributeCommand.commandDelegateOverride(driver, null);

        // Assert
        thrown.expectMessage("control");
    }

    @Test
    public void GetElementAttributeGetAttribute(){
        // Arrange

        // Act
        getElementAttributeCommand.commandDelegateOverride(driver, control);

        // Verify
        verify(driver, times(1)).getElementAttribute(control, attributeName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void CommandDelegate() {
        //Arrange

        //Act
        getElementAttributeCommand.commandDelegate(driver);

        //Verify
        verify(driver, times(1)).getElementAttribute(control, attributeName);
    }
}
