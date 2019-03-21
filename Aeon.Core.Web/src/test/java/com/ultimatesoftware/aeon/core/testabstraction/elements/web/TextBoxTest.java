package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.ClearCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.SetCommand;
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
public class TextBoxTest {
    private TextBox textBox;
    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    @Mock
    private IAdapter adapter;

    @Mock
    private IDriver driver;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Mock
    private IByWeb selector;

    @BeforeEach
    public void setup() {
        //creating an AutomationInfo b/c need to mock a CommandExecutionFacade
        automationInfo = new AutomationInfo(configuration, driver, adapter);
        //Set the facade so the set and clear commands can get it
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        //new TextBox for each test
        textBox = new TextBox(automationInfo, selector);
    }

    @Test
    public void setCallsExecuteWithAnyInput() {
        //Act
        textBox.set(null);  //do not care about the value param

        //Assert
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(automationInfo), any(SetCommand.class));
    }

    @Test
    public void clearCallsExecuteWithAnyInput() {
        //Act
        textBox.clear();

        //Assert
        //Must use eq b/c argument matching is all or nothing
        //We are argument matching for all args of the execute func
        verify(commandExecutionFacade, times(1))
                .execute(Mockito.eq(automationInfo), any(ClearCommand.class));
    }
}
