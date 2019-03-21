package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SetTextValueByJavaScriptCommandTests {
    private SetTextByJavaScriptCommand setTextByJavaScriptCommandObject;
    private String value;

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @Mock
    private Consumer<IDriver> consumer;

    @BeforeEach
    public void setup() {
        setTextByJavaScriptCommandObject = new SetTextByJavaScriptCommand(selector, initializer, value);
    }

    @Test
    public void commandDelegateExecutesSetDivValueByJavaScript() {
        // Arrange
        when(initializer.setContext()).thenReturn(consumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> test = setTextByJavaScriptCommandObject.getCommandDelegate();
        test.accept(driver);

        // Assert
        verify(driver, times(1)).setTextByJavaScript(control, value);
    }
}
