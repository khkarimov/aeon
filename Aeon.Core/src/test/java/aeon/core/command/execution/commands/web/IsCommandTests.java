package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Consumer;

public class IsCommandTests {

    private IsCommand isCommandObjectRaw;
    private IsCommand isCommandObjectText;
    private String value = "Value";
    private ComparisonOption rawOption = ComparisonOption.Raw;
    private ComparisonOption textOption = ComparisonOption.Text;
    private String attribute = "Attribute";

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;
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
        // two isCommandObjects are created to test both ComparisonOption enums
        isCommandObjectRaw = new IsCommand(selector, initializer, value, rawOption, attribute);
        isCommandObjectText = new IsCommand(selector, initializer, value, textOption, attribute);
    }

    @Test
    public void commandDelegateIsCommand(){
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> actionRaw = isCommandObjectRaw.getCommandDelegate();
        Consumer<IDriver> actionText = isCommandObjectText.getCommandDelegate();
        actionRaw.accept(driver);
        actionText.accept(driver);

        // Assert
        verify(driver, times(1)).is(control, value, rawOption, attribute);
        verify(driver, times(1)).is(control, value, textOption, attribute);
    }
}