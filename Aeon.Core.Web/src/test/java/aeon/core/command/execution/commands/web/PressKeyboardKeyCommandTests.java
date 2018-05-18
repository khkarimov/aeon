package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.KeyboardKey;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    @EnumSource(value = KeyboardKey.class, names = {"UP","TAB","SHIFT","END","DOWN","INSERT","DELETE","ESCAPE"})
    public void commandDelegatePressKeyboardKeyCommand(KeyboardKey key){
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
