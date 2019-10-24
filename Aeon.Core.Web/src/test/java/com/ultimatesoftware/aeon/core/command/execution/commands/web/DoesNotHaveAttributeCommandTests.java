package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DoesNotHaveAttributeCommandTests {

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Test
    void testCommandDelegate_callsDriverCorrectly() {
        // Arrange
        DoesNotHaveAttributeCommand doesNotHaveAttributeCommand = new DoesNotHaveAttributeCommand(selector, initializer, "attribute-name");

        // Act
        doesNotHaveAttributeCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).doesNotHaveAttribute(control, "attribute-name");
    }

    @Test
    void testGetAttributeName_returnsAttributeName() {
        // Arrange
        DoesNotHaveAttributeCommand doesNotHaveAttributeCommand = new DoesNotHaveAttributeCommand(selector, initializer, "attribute-name");

        // Act
        String attributeName = doesNotHaveAttributeCommand.getAttributeName();

        // Verify
        assertEquals("attribute-name", attributeName);
    }
}
