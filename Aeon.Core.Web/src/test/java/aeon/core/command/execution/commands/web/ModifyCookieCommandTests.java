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

public class ModifyCookieCommandTests {
    private ModifyCookieCommand modifyCookieCommand;
    private String name = "cookieName";
    private String value = "cookieValues";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup(){ modifyCookieCommand = new ModifyCookieCommand(name, value);}

    @Test
    public void commandDelegateModifyCookieCommand(){
        //Arrange

        //Act
        modifyCookieCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).modifyCookie(name, value);
    }
}
