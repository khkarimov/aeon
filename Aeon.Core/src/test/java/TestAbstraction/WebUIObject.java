package TestAbstraction;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.web.DismissAlertCommand;
import aeon.core.command.execution.commands.web.SendKeysToAlertCommand;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.models.Browser;
import aeon.core.testabstraction.product.Configuration;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.swing.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by josepe on 1/24/2017.
 */

public class WebUIObject {
    // Variables
    private Browser browserObject;
    private AutomationInfo automationInfo;

    // Mocks
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Configuration configuration;
    @Mock
    private IAdapter adapter;
    @Mock
    private IDriver driver;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    // Setup Methods
    @Before
    public void setUp() {
        automationInfo = new AutomationInfo(configuration, driver, adapter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        browserObject = new Browser(automationInfo);
    }

    // Test Methods
    @Test
    public void sendKeysToAlert_ValidString_CallsExecuteWithCorrectKeys() {
        browserObject.sendKeysToAlert("Go Gators!");
        //TODO: Verify whether the correct keys are passed along to SendKeysToAlert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SendKeysToAlertCommand.class));
    }

    @Test
    public void dismissAlert_CallsExecute() {
        browserObject.dismissAlert();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DismissAlertCommand.class));
    }
}
