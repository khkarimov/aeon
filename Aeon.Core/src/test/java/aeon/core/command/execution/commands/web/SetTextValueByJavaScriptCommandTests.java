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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    private IBy selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

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
        // Act
        setTextByJavaScriptCommandObject.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).setTextByJavaScript(control, value);
    }
}
