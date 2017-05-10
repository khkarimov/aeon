package TestAbstraction;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.DoubleClickCommand;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bryant on 5/10/17.
 */
public class DoubleClickCommand_Test {
    private DoubleClickCommand doubleClickCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @Mock
    private Consumer<IDriver> action;

    @Before
    public void setup() {
        doubleClickCommandObject = new DoubleClickCommand(selector, commandInitializer);
    }

    @Test
    public void commandDelegateExecutesDoubleClick() {
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);
        Consumer<IDriver> test = doubleClickCommandObject.getCommandDelegate();
        test.accept(driver);
        verify(driver, times(1)).doubleClick(control);
    }
}
