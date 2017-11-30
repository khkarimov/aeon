package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bryant on 5/15/17.
 */
public class SetTextValueByJavaScriptCommandTests {
    private SetTextByJavaScriptCommand setTextByJavaScriptCommandObject;
    private String value;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Before
    public void setup(){
        setTextByJavaScriptCommandObject = new SetTextByJavaScriptCommand(selector, initializer, value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArguementThrownWhenDriverIsNull(){
        // Arrange

        // Act
        setTextByJavaScriptCommandObject.commandDelegate(null, control);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test
    public void commandDelegateExecutesSetDivValueByJavaScript(){
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
