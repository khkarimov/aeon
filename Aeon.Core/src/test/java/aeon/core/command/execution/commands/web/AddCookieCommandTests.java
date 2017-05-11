package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddCookieCommandTests {
    private AddCookieCommand cookieCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebCookie cookie;
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        cookieCommand = new AddCookieCommand(cookie);
    }

    @Test
    public void testDriverDelegate() {
        // Arrange

        // Act
        Consumer<IDriver> action = cookieCommand.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).addCookie(cookie);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver() {
        // Arrange

        // Act
        Consumer<IDriver> action = cookieCommand.getCommandDelegate();
        action.accept(null);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("driver");

        // Assert
        verify(driver, times(0)).addCookie(cookie);
    }
}
