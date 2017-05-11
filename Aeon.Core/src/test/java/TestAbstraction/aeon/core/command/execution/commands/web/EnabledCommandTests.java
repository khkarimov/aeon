package TestAbstraction.aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.EnabledCommand;
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

public class EnabledCommandTests {

    private EnabledCommand command;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    IWebDriver driver;

    @Mock
    WebControl control;

    @Mock
    private Consumer<IDriver> driverConsumer;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    IBy selector;

    @Before
    public void setUp() {
        command = new EnabledCommand(selector, commandInitializer);
    }

    @Test
    public void enabledCommand_CallsExecute() {
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);
        verify(driver, times(1)).isElementEnabled(control);
    }
}
