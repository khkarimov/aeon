package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.CheckCommand;
import aeon.core.command.execution.commands.web.NotSelectedCommand;
import aeon.core.command.execution.commands.web.SelectedCommand;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RadioButtonTests {

    // Radio button with two argument constructor
    private RadioButton radioButton1;
    // Radio button with three argument constructor
    private RadioButton radioButton2;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AutomationInfo info1;
    @Mock
    private AutomationInfo info2;

    @Mock
    private IBy selector;

    @Mock
    Iterable<IBy> switchMechanism;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Before
    public void setup(){
        info1 = new AutomationInfo(configuration, driver, adapter);
        info1.setCommandExecutionFacade(commandExecutionFacade);
        radioButton1 = new RadioButton(info1, selector);

        info2 = new AutomationInfo(configuration, driver, adapter);
        info2.setCommandExecutionFacade(commandExecutionFacade);
        radioButton2 = new RadioButton(info2, selector, switchMechanism);
    }

    @Test
    public void selectedExecute() {
        //Act
        radioButton1.selected();
        radioButton2.selected();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(SelectedCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(SelectedCommand.class));
    }

    @Test
    public void notSelectedExecute() {
        //Act
        radioButton1.notSelected();
        radioButton2.notSelected();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(NotSelectedCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(NotSelectedCommand.class));
    }

    @Test
    public void checkExecute() {
        //Act
        radioButton1.check();
        radioButton2.check();

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info1), any(CheckCommand.class));
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(info2), any(CheckCommand.class));
    }


}
