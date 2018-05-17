package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DisabledCommandTests {

    private DisabledCommand disabledCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @BeforeEach
    public void setup(){
        disabledCommandObject = new DisabledCommand(selector, initializer);
    }

    @Test
    public void DriverNullThrowsException(){
        // Arrange
        Throwable exception;

        // Act
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> disabledCommandObject.commandDelegate(null, control));

        // Assert
        Assertions.assertEquals("driver", exception.getMessage());
    }

    @Test
    public void ControlNullThrowsException(){
        // Arrange
        Throwable exception;

        // Act
        exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> disabledCommandObject.commandDelegate(driver, null));

        // Assert
        Assertions.assertEquals("control", exception.getMessage());
    }

    @Test
    public void DisabledCommandElementDisabled(){
        // Act
        disabledCommandObject.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).isElementDisabled(control);
    }
}
