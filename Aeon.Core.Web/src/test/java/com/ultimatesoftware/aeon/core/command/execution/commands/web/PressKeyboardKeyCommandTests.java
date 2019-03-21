package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PressKeyboardKeyCommandTests {

    private PressKeyboardKeyCommand pressKeyboardKeyCommandObject;

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    private KeyboardKey key;

    @ParameterizedTest
    @EnumSource(value = KeyboardKey.class, names = {"UP", "TAB", "SHIFT", "END", "DOWN", "INSERT", "DELETE", "ESCAPE"})
    public void commandDelegatePressKeyboardKeyCommand(KeyboardKey key) {
        //Arrange
        this.key = key;
        pressKeyboardKeyCommandObject = new PressKeyboardKeyCommand(selector, initializer, this.key);
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = pressKeyboardKeyCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).pressKeyboardKey(control, key);
    }
}
