package aeon.core.command.execution.commands.web;

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
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ExecuteScriptCommandTests {

    private ExecuteScriptCommand executeScriptCommand;
    private String script = "";

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setUp() {
        executeScriptCommand = new ExecuteScriptCommand(script);
    }

    @Test
    public void commandDelegateExecuteScriptCommand() {
        // Arrange

        // Act
        executeScriptCommand.commandDelegate(driver);

        // Assert
        verify(driver, times(1)).executeScript(script);
    }
}
