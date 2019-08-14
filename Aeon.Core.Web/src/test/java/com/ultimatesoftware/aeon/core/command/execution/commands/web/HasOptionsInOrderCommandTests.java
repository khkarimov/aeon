package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
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
public class HasOptionsInOrderCommandTests {

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
        HasOptionsInOrderCommand command = new HasOptionsInOrderCommand(selector, commandInitializer, options, optGroup, WebSelectOption.TEXT);
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).hasOptionsInOrder(control, options, optGroup, WebSelectOption.TEXT);
    }

    @Test
    public void hasOptionsCommandFirstConstructor() {
        // Arrange
        HasOptionsInOrderCommand commandFirst = new HasOptionsInOrderCommand(selector, commandInitializer, options, WebSelectOption.TEXT);

        // Act
        commandFirst.commandDelegate(driver, control);

        // Assert
        verify(driver, times(1)).hasOptionsInOrder(control, options, null, WebSelectOption.TEXT);
    }
}
