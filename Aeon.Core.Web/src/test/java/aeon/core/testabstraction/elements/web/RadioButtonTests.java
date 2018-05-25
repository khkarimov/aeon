package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.CheckCommand;
import aeon.core.command.execution.commands.web.NotSelectedCommand;
import aeon.core.command.execution.commands.web.SelectedCommand;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
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
public class RadioButtonTests {

    // Radio button with two argument constructor
    private RadioButton radioButton1;
    // Radio button with three argument constructor
    private RadioButton radioButton2;

    @Mock
    private AutomationInfo info1;
    @Mock
    private AutomationInfo info2;

    @Mock
    private IByWeb selector;

    @Mock
    private Iterable<IByWeb> switchMechanism;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @BeforeEach
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
