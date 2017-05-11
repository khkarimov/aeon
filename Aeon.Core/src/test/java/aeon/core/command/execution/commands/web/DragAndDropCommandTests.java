package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
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
    private IBy dropElement;

    @Mock
    private IBy targetElement;

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
        assertNotNull(dragAndDropCommandObject.targetElement);
        //assertTrue(dragAndDropCommandObject.targetElement instanceof IBy);
    }

    @Test
    public void illegalArgumentThrownWhenDriverIsNull(){
        // Arrange
        driver = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("driver");

        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);
    }

    @Test
    public void illegalArgumentThrownWhenControlIsNull(){
        // Arrange
        control = null;
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("control");

        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);
    }

    @Test
    public void commandDelegateExecutesDragAndDrop() {
        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).dragAndDrop(control, targetElement);
    }
}
