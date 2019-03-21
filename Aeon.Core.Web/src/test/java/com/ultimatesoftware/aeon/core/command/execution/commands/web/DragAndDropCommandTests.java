package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class DragAndDropCommandTests {
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
    public void setup() {
        dragAndDropCommandObject = new DragAndDropCommand(dropElement, targetElement, commandInitializer);
    }

    @Test
    public void targetElementSetWhenObjectIsCreated() {
        // Assert
        Assertions.assertNotNull(dragAndDropCommandObject.targetElement);
        Assertions.assertEquals(dragAndDropCommandObject.targetElement, targetElement);
    }

    @Test
    public void commandDelegateExecutesDragAndDrop() {
        // Act
        dragAndDropCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).dragAndDrop(control, targetElement);
    }
}
