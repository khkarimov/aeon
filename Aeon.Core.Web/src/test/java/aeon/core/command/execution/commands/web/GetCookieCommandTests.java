package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class GetCookieCommandTests {
    private  GetCookieCommand getCookieCommandObject;
    private String cookie;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup() { getCookieCommandObject = new GetCookieCommand(cookie); }


    @Test
    public void commandDelegateGetCookieCommand(){
        //Arrange

        // Act
        getCookieCommandObject.commandDelegate(driver);

        // Assert
        verify(driver, times(1)).getCookie(cookie);
    }
}
