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

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class IsNotLikeCommandTests {

    private IsNotLikeCommand isNotLikeCommandObjectRaw;
    private IsNotLikeCommand isNotLikeCommandObjectText;
    private String value = "Value";
    private ComparisonOption rawOption = ComparisonOption.Raw;
    private ComparisonOption textOption = ComparisonOption.Text;
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

    @BeforeEach
    public void setup() {
        // two isNotLikeCommandObjects are created to test both ComparisonOption enums
        isNotLikeCommandObjectRaw = new IsNotLikeCommand(selector, initializer, value, rawOption, attribute);
        isNotLikeCommandObjectText = new IsNotLikeCommand(selector, initializer, value, textOption, attribute);
    }

    @Test
    public void commandDelegateIsNotLikeCommandWithRaw() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionRaw = isNotLikeCommandObjectRaw.getCommandDelegate();
        actionRaw.accept(driver);

        // Assert
        verify(driver, times(1)).isNotLike(control, value, rawOption, attribute);
    }

    @Test
    public void commandDelegateIsNotLikeCommandWithText() {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionText = isNotLikeCommandObjectText.getCommandDelegate();
        actionText.accept(driver);

        // Assert
        verify(driver, times(1)).isNotLike(control, value, textOption, attribute);
    }
}