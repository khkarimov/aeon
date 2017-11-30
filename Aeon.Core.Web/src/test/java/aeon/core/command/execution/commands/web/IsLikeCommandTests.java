package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IsLikeCommandTests {

    private IsLikeCommand isLikeCommandObjectRaw;
    private IsLikeCommand isLikeCommandObjectText;
    private String value = "Value";
    private ComparisonOption rawOption = ComparisonOption.Raw;
    private ComparisonOption textOption = ComparisonOption.Text;
    private String attribute = "Attribute";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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
    @Before
    public void setup() {
        // two isLikeCommandObjects are created to test both ComparisonOption enums
        isLikeCommandObjectRaw = new IsLikeCommand(selector, initializer, value, rawOption, attribute);
        isLikeCommandObjectText = new IsLikeCommand(selector, initializer, value, textOption, attribute);
    }

    @Test
    public void commandDelegateIsLikeCommandWithRaw(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionRaw = isLikeCommandObjectRaw.getCommandDelegate();
        actionRaw.accept(driver);

        // Assert
        verify(driver, times(1)).isLike(control, value, rawOption, attribute);
    }

    @Test
    public void commandDelegateIsLikeCommandWithText(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionText = isLikeCommandObjectText.getCommandDelegate();
        actionText.accept(driver);

        // Assert
        verify(driver, times(1)).isLike(control, value, textOption, attribute);
    }
}