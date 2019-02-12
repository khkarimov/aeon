package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class HasOptionsCommandTests {

    @Mock
    WebControl control;
    @Mock
    Consumer<IDriver> action;
    private String[] options = {"test", "test1", "test2", "test3"};
    private String optGroup = "teststes";
    @Mock
    private IWebDriver driver;
    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer commandInitializer;

    @Test
    public void commandDelegateHasOptionsCommand() {
        // Arrange
        HasOptionsCommand command = new HasOptionsCommand(selector, commandInitializer, options, optGroup, WebSelectOption.Text);
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).hasOptions(control, options, optGroup, WebSelectOption.Text);
    }

    @Test
    public void hasOptionsCommandFirstConstructor() {
        // Arrange
        HasOptionsCommand commandFirst = new HasOptionsCommand(selector, commandInitializer, options, WebSelectOption.Text);

        // Act
        commandFirst.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).hasOptions(control, options, null, WebSelectOption.Text);
    }
}
