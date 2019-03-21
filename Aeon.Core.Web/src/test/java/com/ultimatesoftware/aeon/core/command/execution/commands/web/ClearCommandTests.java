package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.exceptions.Select2Exception;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ClearCommandTests {
    private ClearCommand clearCommand;

    @Mock
    private IByWeb selector;

    @Mock
    private ByJQuery byJQuery;

    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IWebDriver driver;

    @Mock
    private WebControl control;

    @Mock
    private Consumer<IDriver> action;

    @BeforeEach
    public void setup() {
        clearCommand = new ClearCommand(selector, commandInitializer);

        // Arrange
        when(commandInitializer.setContext()).thenReturn(action);
    }

    @Test
    public void commandDelegateIllegalArgumentException() {
        // Act
        // Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clearCommand.getCommandDelegate().accept(null));
    }

    @Test
    public void commandDelegateClearElementExecutedWithoutExecutingScript() {
        // Arrange
        when(driver.getElementTagName(control)).thenReturn("FOO");
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        clearCommand.getCommandDelegate().accept(driver);

        //Assert
        verify(driver, times(1)).clearElement(Mockito.eq(control));
        verify(driver, times(0)).executeScript(any(String.class));
    }

    @Test
    public void commandDelegateClearElementExecutedAndExecutingScript() {
        // Arrange
        when(driver.getElementTagName(control)).thenReturn("SELECT");
        when(driver.executeScript(any(String.class))).thenReturn(true);
        when(control.getSelector()).thenReturn(selector);
        when(selector.toJQuery()).thenReturn(byJQuery);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        // Assert
        Assertions.assertThrows(Select2Exception.class,
                () -> clearCommand.getCommandDelegate().accept(driver));
        verify(driver, times(0)).clearElement(Mockito.eq(control));
        verify(driver, times(1)).executeScript(any(String.class));
    }

    @Test
    public void commandDelegateSelect2Exception() {
        // Arrange
        when(driver.getElementTagName(control)).thenReturn("SELECT");
        when(driver.executeScript(any(String.class))).thenReturn(false);
        when(control.getSelector()).thenReturn(selector);
        when(selector.toJQuery()).thenReturn(byJQuery);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        clearCommand.getCommandDelegate().accept(driver);

        // Assert
        verify(driver, times(1)).clearElement(Mockito.eq(control));
    }
}
