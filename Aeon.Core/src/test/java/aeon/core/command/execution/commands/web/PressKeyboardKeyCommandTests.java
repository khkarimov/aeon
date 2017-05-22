package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.KeyboardKey;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PressKeyboardKeyCommandTests {

    private PressKeyboardKeyCommand pressKeyboardKeyCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IBy selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    private KeyboardKey key;

    @Before
    public void setup(){
        pressKeyboardKeyCommandObject = new PressKeyboardKeyCommand(selector, initializer, key);
    }

    public PressKeyboardKeyCommandTests(KeyboardKey key){
        this.key = key;
    }

    @Parameterized.Parameters
    public static Collection<KeyboardKey []> data(){
        return Arrays.asList(new KeyboardKey[][] {
                {KeyboardKey.UP}, {KeyboardKey.TAB}, {KeyboardKey.SHIFT}, {KeyboardKey.END},
                {KeyboardKey.DOWN}, {KeyboardKey.INSERT}, {KeyboardKey.DELETE}, {KeyboardKey.ESCAPE}
        });
    }

    @Test
    public void commandDelegatePressKeyboardKeyCommand(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = pressKeyboardKeyCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).pressKeyboardKey(control, key);
    }
}
