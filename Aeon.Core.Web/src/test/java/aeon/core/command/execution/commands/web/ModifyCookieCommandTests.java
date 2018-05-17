package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ModifyCookieCommandTests {
    private ModifyCookieCommand modifyCookieCommand;
    private String name = "cookieName";
    private String value = "cookieValues";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup() {
        modifyCookieCommand = new ModifyCookieCommand(name, value);
    }

    @Test
    public void commandDelegateModifyCookieCommand(){
        //Arrange

        //Act
        modifyCookieCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).modifyCookie(name, value);
    }
}
