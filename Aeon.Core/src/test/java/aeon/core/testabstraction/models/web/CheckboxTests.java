package aeon.core.testabstraction.models.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.CheckCommand;
import aeon.core.command.execution.commands.web.NotSelectedCommand;
import aeon.core.command.execution.commands.web.SelectedCommand;
import aeon.core.command.execution.commands.web.UnCheckCommand;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.elements.web.Checkbox;
import aeon.core.testabstraction.product.Configuration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;


public class CheckboxTests {

    private Checkbox checkbox1;
    private Checkbox checkbox2;

    private AutomationInfo info;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;
    @Mock
    private Iterable<IBy> switchMechanism;
    @Mock
    private IDriver driver;
    @Mock
    private IAdapter adapter;
    @Mock
    private Configuration configuration;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;


    @Before
    public void setup() {

        info = new AutomationInfo(configuration, driver, adapter);

        info.setCommandExecutionFacade(commandExecutionFacade);

        checkbox1 = new Checkbox(info, selector);
        checkbox2 = new Checkbox(info, selector, switchMechanism);
    }

    @Test
    public void checkCall_VerifyExecute() {
        checkbox1.check();
        checkbox2.check();

        //Should be called twice: once for checkbox1 and once for checkbox2
        verify(commandExecutionFacade, times(2)).execute(Mockito.eq(info), any(CheckCommand.class));

    }

    @Test
    public void uncheckCall_VerifyExecute() {
        checkbox1.uncheck();
        checkbox2.uncheck();

        //Should be called twice: once for checkbox1 and once for checkbox2
        verify(commandExecutionFacade, times(2)).execute(Mockito.eq(info), any(UnCheckCommand.class));
    }

    @Test
    public void selectedCall_VerifyExecute() {
        checkbox1.selected();
        checkbox2.selected();

        //Should be called twice: once for checkbox1 and once for checkbox2
        verify(commandExecutionFacade, times(2)).execute(Mockito.eq(info), any(SelectedCommand.class));
    }

    @Test
    public void notSelectedCall_VerifyExecute() {
        checkbox1.notSelected();
        checkbox2.notSelected();

        //Should be called twice: once for checkbox1 and once for checkbox2
        verify(commandExecutionFacade, times(2)).execute(Mockito.eq(info), any(NotSelectedCommand.class));
    }
}
