package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
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
public class HasOptionsInOrderCommandTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
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
        HasOptionsInOrderCommand command = new HasOptionsInOrderCommand(selector, commandInitializer, options, optGroup, WebSelectOption.Text);
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).hasOptionsInOrder(control, options, optGroup, WebSelectOption.Text);
    }

    @Test
    public void hasOptionsCommandFirstConstructor(){
        // Arrange
        HasOptionsInOrderCommand commandFirst = new HasOptionsInOrderCommand(selector, commandInitializer, options, WebSelectOption.Text);

        // Act
        commandFirst.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).hasOptionsInOrder(control, options, null, WebSelectOption.Text);
    }
}
