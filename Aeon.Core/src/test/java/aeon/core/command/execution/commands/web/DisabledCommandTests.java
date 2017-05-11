package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DisabledCommandTests {

    private DisabledCommand disabledCommandObject;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock
    private IBy selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Before
    public void setup(){
        disabledCommandObject = new DisabledCommand(selector, initializer);
    }

    @Test
    public void DriverNullThrowsException(){
        // Arrange
        driver = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("driver");

        // Act
        disabledCommandObject.commandDelegate(driver, control);
    }
    @Test
    public void ControlNullThrowsException(){
        // Arrange
        control = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("control");

        // Act
        disabledCommandObject.commandDelegate(driver, control);
    }
    @Test
    public void DisabledCommandElementDisabled(){
        // Arrange and Act
        disabledCommandObject.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).isElementDisabled(control);
    }
}
