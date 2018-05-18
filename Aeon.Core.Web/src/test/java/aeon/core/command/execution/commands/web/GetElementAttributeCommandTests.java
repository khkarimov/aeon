package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
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
public class GetElementAttributeCommandTests {

    private GetElementAttributeCommand getElementAttributeCommand;

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private  IWebDriver driver;
    @Mock
    private WebControl control;

    private String attributeName = "Name";

    @BeforeEach
    public void setup() { getElementAttributeCommand = new GetElementAttributeCommand(selector, initializer, attributeName); }

    @Test
    public void DriverNullThrowsException(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> getElementAttributeCommand.commandDelegateOverride(null, control));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
    }

    @Test
    public void ControlNullThrowsException(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> getElementAttributeCommand.commandDelegateOverride(driver, null));

        // Assert
        Assertions.assertEquals("control", illegalArgumentException.getMessage());
    }

    @Test
    public void GetElementAttributeGetAttribute(){
        // Arrange

        // Act
        getElementAttributeCommand.commandDelegateOverride(driver, control);

        // Verify
        verify(driver, times(1)).getElementAttribute(control, attributeName);
    }

    @Test
    public void CommandDelegate() {
        //Arrange

        //Act
        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> getElementAttributeCommand.commandDelegate(driver));
        verify(driver, times(0)).getElementAttribute(control, attributeName);
    }
}
