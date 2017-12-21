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

public class DeleteCookieCommandTests {
    private DeleteCookieCommand deleteCookie;
    private String cookie = "cookie";
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setUp(){deleteCookie = new DeleteCookieCommand(cookie);}
    @Test
    public void testDriverDelegate() {
        //Arrange

        //Act
        //deleteCookie.getCommandDelegate();
        deleteCookie.driverDelegate(driver);
        //Verify
        verify(driver, times(1)).deleteCookie(cookie);

    }
    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Act
        deleteCookie.driverDelegate(null);
    }



}
