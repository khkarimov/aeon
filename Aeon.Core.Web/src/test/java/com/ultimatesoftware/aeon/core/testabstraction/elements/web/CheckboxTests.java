package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.CheckCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.NotSelectedCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.SelectedCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.UnCheckCommand;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CheckboxTests {

    private Checkbox checkbox1;
    private Checkbox checkbox2;

    private AutomationInfo info;

    @Mock
    private IByWeb selector;
    @Mock
    private IByWeb switchMechanism;
    @Mock
    private IDriver driver;
    @Mock
    private IAdapter adapter;
    @Mock
    private Configuration configuration;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;


    @BeforeEach
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
