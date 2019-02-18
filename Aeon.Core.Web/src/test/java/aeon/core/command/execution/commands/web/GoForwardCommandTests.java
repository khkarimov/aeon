package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
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
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GoForwardCommandTests {

    private GoForwardCommand goForwardCommandObject;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;

    /* SETUP */

    @BeforeEach
    public void setup() {
        goForwardCommandObject = new GoForwardCommand();
    }

    /* TEST CASES */

    /**
     * Move forward a single entry in the browser's history.
     * Usage:
     * Context.browser.goForward();
     * <p>
     * Does nothing if we are on the latest page viewed.
     */
    @Test
    public void commandDelegateGoForwardCommand() {
        //Arrange

        //Act
        Consumer<IDriver> action = goForwardCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).goForward();
    }
}
