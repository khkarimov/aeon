package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetClientRectsCommandTests {

    private GetClientRectsCommand getClientRectsCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @Before
    public void setup() {
        getClientRectsCommand = new GetClientRectsCommand(selector, initializer);
    }

    @Test
    public void setGetClientRectsCommand_CallsExecute() {

        // Act
        getClientRectsCommand.commandDelegateOverride(driver, control);

        // Assert
        verify(driver, times(1)).getClientRects(control);
    }
}