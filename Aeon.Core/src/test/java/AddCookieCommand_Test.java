import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.web.AddCookieCommand;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by setht on 5/9/17.
 * Class created to test AddCookieCommand.java
 * @author setht
 */

public class AddCookieCommand_Test {
    private AddCookieCommand cookieCommand;

    //Mocks
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebCookie cookie;
    @Mock
    private IWebDriver driver;

    //Setup
    @Before
    public void setUp() {
        cookieCommand = new AddCookieCommand(cookie);
    }

    @Test
    public void testDriverDelegate() {
        Consumer<IDriver> action = cookieCommand.getCommandDelegate();
        action.accept(driver);
        verify(driver, times(1)).addCookie(cookie);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver() {
        Consumer<IDriver> action = cookieCommand.getCommandDelegate();
        action.accept(null);
        verify(driver, times(0)).addCookie(cookie);
    }
}
