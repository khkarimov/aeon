package TestAbstraction;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

import java.util.Locale;
import java.util.function.Consumer;

/**
 * Created by coltonm on 5/9/17.
 */
public class Click_Test {

    //Variables
    private ClickCommand clickCommandObject;

    //Mocks
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

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
    @Before
    public void setup() {
        clickCommandObject = new ClickCommand(selector, initializer);
    }

    @Test
    public void commandDelegateClickCommand(){
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);
        Consumer<IDriver> action = clickCommandObject.getCommandDelegate();
        action.accept(driver);
        verify(driver, times(1)).click(control);
    }
}
