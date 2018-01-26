
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

public class SwitchToWindowByUrlCommandTests {

    private SwitchToWindowByUrlCommand switchToWindowByUrlObject;
    private String testUrl = "url";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        switchToWindowByUrlObject = new SwitchToWindowByUrlCommand(testUrl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void DriverNullThrowsException(){
        //Arrange

        // Act
        switchToWindowByUrlObject.commandDelegate(null);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test
    public void commandDelegateSwitchToWindowByUrlCommand() {
        //Arrange

        //Act
        switchToWindowByUrlObject.commandDelegate(driver);

        //Assert
        verify(driver, times(1)).switchToWindowByUrl(testUrl);
    }

}
