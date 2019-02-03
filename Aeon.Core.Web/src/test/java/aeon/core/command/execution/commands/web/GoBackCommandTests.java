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
public class GoBackCommandTests {
    private GoBackCommand goBackCommandObject;

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;

    @BeforeEach
    public void setup() {
        goBackCommandObject = new GoBackCommand();
    }


    /**
     * Move back a single page in a browser's history
     * Usage:
     * Context.browser.goBack();
     * <p>
     * If no previous pages, it does nothing
     */
    @Test
    public void commandDelegateGoBackCommand() {
        //Arrange

        //Act
        Consumer<IDriver> action = goBackCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).goBack();
    }
}
