package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SetDivValueByJavaScriptCommandTests {
    private SetDivValueByJavaScriptCommand setDivValueByJavaScriptCommandObject;
    private String value;

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
    private Consumer<IDriver> consumer;

    @Before
    public void setup(){
        setDivValueByJavaScriptCommandObject = new SetDivValueByJavaScriptCommand(selector, initializer, value);
    }

    @Test
    public void commandDelegateExecutesSetDivValueByJavaScript(){
        // Arrange
        when(initializer.setContext()).thenReturn(consumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> test = setDivValueByJavaScriptCommandObject.getCommandDelegate();
        test.accept(driver);

        // Assert
        verify(driver, times(1)).setDivValueByJavaScript(control, value);
    }
}
