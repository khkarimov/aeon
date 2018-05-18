package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.exceptions.Select2Exception;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);
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

        // Act
        clearCommand.getCommandDelegate().accept(driver);

        // Assert
        verify(driver, times(1)).clearElement(Mockito.eq(control));
    }
}
