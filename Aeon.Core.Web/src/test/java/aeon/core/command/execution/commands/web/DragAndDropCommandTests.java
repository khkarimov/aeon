package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DragAndDropCommandTests
{
    private DragAndDropCommand dragAndDropCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Before
    public void setup(){
        dragAndDropCommandObject = new DragAndDropCommand(dropElement, targetElement, commandInitializer);
    }

    @Test
    public void targetElementSetWhenObjectIsCreated(){
        // Assert
        assertNotNull(dragAndDropCommandObject.targetElement);
        assertEquals(dragAndDropCommandObject.targetElement, targetElement);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentThrownWhenDriverIsNull(){
        // Arrange
        driver = null;

        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentThrownWhenControlIsNull(){
        // Arrange
        control = null;

        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        thrown.expectMessage("control");
    }

    @Test
    public void commandDelegateExecutesDragAndDrop() {
        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).dragAndDrop(control, targetElement);
    }
}
