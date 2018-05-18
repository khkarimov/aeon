package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GetClientRectsCommandTests {

    private GetClientRectsCommand getClientRectsCommand;

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @BeforeEach
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