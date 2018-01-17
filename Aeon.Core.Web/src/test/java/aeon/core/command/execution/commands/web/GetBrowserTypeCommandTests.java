package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GetBrowserTypeCommandTests {
    private GetBrowserTypeCommand getBrowserTypeCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup(){getBrowserTypeCommand = new GetBrowserTypeCommand();}

    @Test
    public void commandDelegateAlertTextLikeCommandTrue(){
        //Arrange

        //Act
        getBrowserTypeCommand.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).getBrowserType();
    }
}
