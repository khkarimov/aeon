package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.framework.abstraction.drivers.IDriver;
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

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GoBackCommandTests {
    private GoBackCommand goBackCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ICommandInitializer initializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private Consumer<IDriver> action;

    @BeforeEach
    public void setup(){goBackCommandObject = new GoBackCommand();}


    /**
     * Move back a single page in a browser's history
     * Usage:
     *  Context.browser.goBack();
     *
     * If no previous pages, it does nothing
     */
    @Test
    public void commandDelegateGoBackCommand(){
        //Arrange
        when(initializer.setContext()).thenReturn(action);

        //Act
        Consumer<IDriver> action = goBackCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).goBack();
    }
}
