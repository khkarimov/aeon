package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class GetAlertTextCommandTests {

    private GetAlertTextCommand getAlertTextCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup() {getAlertTextCommand = new GetAlertTextCommand();}

    @Test
    public void commandDelegateGetAlertText(){

        // Act
        getAlertTextCommand.commandDelegate(driver);

        // Assert
        verify(driver, times(1)).getAlertText();
    }
}

