package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class IsCommandTests {

    private String value = "Value";
    private String attribute = "Attribute";

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;

    @Test
    public void commandDelegateIsCommandWithRaw(){
        // Arrange
        IsCommand isCommandObjectRaw = new IsCommand(selector, initializer, value, ComparisonOption.Raw, attribute);
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionRaw = isCommandObjectRaw.getCommandDelegate();
        actionRaw.accept(driver);

        // Assert
        verify(driver, times(1)).is(control, value, ComparisonOption.Raw, attribute);
    }

    @Test
    public void commandDelegateIsCommandWithText(){
        // Arrange
        IsCommand isCommandObjectText = new IsCommand(selector, initializer, value, ComparisonOption.Text, attribute);
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionText = isCommandObjectText.getCommandDelegate();
        actionText.accept(driver);

        // Assert
        verify(driver, times(1)).is(control, value, ComparisonOption.Text, attribute);
    }
}