package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GoToUrlCommandTests {
    private GoToUrlCommand goToUrlCommand;
    private String urlString = "https://www.google.com";
    private URL url = new URL("https://www.google.com");

    @Mock
    private IWebDriver driver;

    public GoToUrlCommandTests() throws MalformedURLException {
    }

    @BeforeEach
    public void setup() {
        goToUrlCommand = new GoToUrlCommand(urlString);
    }

    @Test
    public void commandDelegateGoToUrlCommand() {
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
