package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;

import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.MockitoJUnit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.MalformedURLException;
import java.net.URL;

public class VerifyUrlCommandTests {
    private VerifyUrlCommand verifyUrlCommand;
    private String comparingURL = "http://google.com";
    private URL url = new URL("http://google.com");

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    public VerifyUrlCommandTests() throws MalformedURLException {
    }

    @Before
    public void setUp() { verifyUrlCommand = new VerifyUrlCommand(comparingURL); }

    @Test
    public void driverDelegateVerifyUrlCommand() {
        //Arrange

        //Act
        verifyUrlCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).verifyURL(url);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverDelegateVerifyUrlCommandTestWithNullDriver(){
        // Arrange

        // Act
        verifyUrlCommand.driverDelegate(null);

        //Assert
        expectedException.expect(IllegalArgumentException.class);
    }
}