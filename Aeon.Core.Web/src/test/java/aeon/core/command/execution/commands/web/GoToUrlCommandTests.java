package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.MalformedURLException;
import java.net.URL;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GoToUrlCommandTests {
    private GoToUrlCommand goToUrlCommand;
    private URL url = new URL("https://www.google.com");

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    public GoToUrlCommandTests() throws MalformedURLException {
    }

    @BeforeEach
    public void setup() {
        goToUrlCommand = new GoToUrlCommand(url);
    }

    @Test
    public void commandDelegateGoToUrlCommand(){
        //Arrange

        //Act
        goToUrlCommand.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).goToUrl(url);
    }

    @Test
    public void testCommandDelegateNullDriver() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> goToUrlCommand.commandDelegate(null));
    }
}