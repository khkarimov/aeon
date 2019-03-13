package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class VerifyUrlCommandTests {
    private VerifyUrlCommand verifyUrlCommand;
    private URL url = new URL("http://google.com");

    @Mock
    private IWebDriver driver;

    public VerifyUrlCommandTests() throws MalformedURLException {
    }

    @BeforeEach
    public void setUp() {
        String comparingURL = "http://google.com";
        verifyUrlCommand = new VerifyUrlCommand(comparingURL);
    }

    @Test
    public void driverDelegateVerifyUrlCommand() {
        //Arrange

        //Act
        verifyUrlCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).verifyURL(url);
    }
}
