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

import java.net.MalformedURLException;
import java.net.URL;

public class GoToUrlCommandTests {
    private GoToUrlCommand goToUrlCommand;
    private URL url = new URL("https://www.google.com");

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    public GoToUrlCommandTests() throws MalformedURLException {
    }

    @Before
    public void setup(){ goToUrlCommand = new GoToUrlCommand(url);}

    @Test
    public void commandDelegateGoToUrlCommand(){
        //Arrange

        //Act
        goToUrlCommand.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).goToUrl(url);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCommandDelegateNullDriver() {
        //Arrange

        //Act
        goToUrlCommand.commandDelegate(null);
        expectedException.expect(IllegalArgumentException.class);

        //Assert
    }
}