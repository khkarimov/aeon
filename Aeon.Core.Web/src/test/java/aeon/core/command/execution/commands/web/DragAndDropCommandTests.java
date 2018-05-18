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
public class DragAndDropCommandTests
{
    private DragAndDropCommand dragAndDropCommandObject;

    @Mock
    private IByWeb dropElement;

    @Mock
    private IByWeb targetElement;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @BeforeEach
    public void setup(){
        dragAndDropCommandObject = new DragAndDropCommand(dropElement, targetElement, commandInitializer);
    }

    @Test
    public void targetElementSetWhenObjectIsCreated(){
        // Assert
        Assertions.assertNotNull(dragAndDropCommandObject.targetElement);
        Assertions.assertEquals(dragAndDropCommandObject.targetElement, targetElement);
    }

    @Test
    public void illegalArgumentThrownWhenDriverIsNull(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> dragAndDropCommandObject.commandDelegate(null, control));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
    }

    @Test
    public void illegalArgumentThrownWhenControlIsNull(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> dragAndDropCommandObject.commandDelegate(driver, null));

        // Assert
        Assertions.assertEquals("control", illegalArgumentException.getMessage());
    }

    @Test
    public void commandDelegateExecutesDragAndDrop() {
        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).dragAndDrop(control, targetElement);
    }
}
