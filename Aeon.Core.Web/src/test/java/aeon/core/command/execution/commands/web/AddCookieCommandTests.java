package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddCookieCommandTests {
    private AddCookieCommand cookieCommand;

    @Mock
    private IWebCookie cookie;
    @Mock
    private IWebDriver driver;

    @BeforeEach
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

    @Test
    public void testDriverDelegateNullDriver() {
        // Arrange
        Exception illegalArgumentException;
        Consumer<IDriver> action = cookieCommand.getCommandDelegate();

        // Act
        //Assert
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> action.accept(null));
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
        verify(driver, times(0)).addCookie(cookie);
    }
}
