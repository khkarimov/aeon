package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.ClearCommand;
import aeon.core.command.execution.commands.web.SetCommand;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by chasef on 5/26/17.
 */
public class TextBoxTest {
    private TextBox textBox;  //class under test
    private AutomationInfo automationInfo;  //needed to make a TextBox

    @Rule  //allows the @Mock annotation to be used
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock  //for AutomationInfo constructor
    private Configuration configuration;

    @Mock  //for AutomationInfo constructor
    private IAdapter adapter;

    @Mock  //for AutomationInfo constructor
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Mock  //Needed for TextBox constructor
    private IBy selector;

    @Before
    public void setup() {
        //creating an AutomationInfo b/c need to mock a CommandExecutionFacade
        automationInfo = new AutomationInfo(configuration, driver, adapter);
        //Set the facade so the set and clear commands can get it
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        //new TextBox for each test
        textBox = new TextBox(automationInfo, selector);
    }

    @Test
    public void setCommandExecute() {
       //Act
       textBox.set(null);  //do not care about the value param

       //Assert
       verify(commandExecutionFacade, times(1))
               .execute(Mockito.eq(automationInfo), any(SetCommand.class));
    }

    @Test
    public void clearCommandExecute() {
        //Act
        textBox.clear();

        //Assert
        //Must use eq b/c argument matching is all or nothing
        //We are argument matching for all args of the execute func
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(automationInfo), any(ClearCommand.class));
    }

}
