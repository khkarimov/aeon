package TestAbstraction.aeon.core.command.execution.commands.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.SelectedCommand;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.elements.web.RadioButton;
import aeon.core.testabstraction.product.Configuration;
import com.sun.deploy.association.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.swing.*;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rohanp on 5/9/17.
 */
public class SelectedCommandTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Configuration configuration;
    @Mock
    private Consumer<IDriver> driverConsumer;
    @Mock
    private IAdapter adapter;
    @Mock
    private IWebDriver driver;
    @Mock
    WebControl control;
    @Mock
    private IBy selector;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Mock
    private ICommandInitializer commandInitializer;
    @Mock
    private RadioButton radioButtonObject;
    @Mock
    private AutomationInfo automationInfo;

    private SelectedCommand command;

    @Before
    public void setUp()
    {
//        automationInfo = new AutomationInfo(configuration, driver, adapter);
//        radioButtonObject = new RadioButton(automationInfo, selector);
         command = new SelectedCommand(selector, commandInitializer);
    }

    @Test
    public void selectedCommand_CallsExecute()
    {
       when(commandInitializer.setContext()).thenReturn(driverConsumer);
       when(commandInitializer.findElement(driver, selector)).thenReturn(control);
       Consumer<IDriver> action = command.getCommandDelegate();
       action.accept(driver);
       verify(driver, times(1)).selected(control);
    }

}
