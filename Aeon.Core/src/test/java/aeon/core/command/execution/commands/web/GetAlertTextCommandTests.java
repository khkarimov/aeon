package aeon.core.command.execution.commands.web;


import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetAlertTextCommandTests {

    private GetAlertTextCommand getAlertTextCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup() { getAlertTextCommand = new GetAlertTextCommand(); }

    @Test
    public void GetElementAttributeGetAttribute(){

        // Arrange

        // Act
        Object text = getAlertTextCommand.commandDelegate(driver);

        // Verify
        verify(driver, times(1)).getAlertText();
        assertTrue(text instanceof String);
    }
}
