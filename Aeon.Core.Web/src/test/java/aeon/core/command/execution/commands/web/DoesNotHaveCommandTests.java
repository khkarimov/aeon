package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
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
public class DoesNotHaveCommandTests {

    DoesNotHaveCommand command;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IByWeb selector;

    @Mock
    private ICommandInitializer initializer;

    private String [] messages = {"test", "test1", "test2", "test3"};

    private String str = "teststes";

    private String atr = "testeests";

    @Mock
    private IWebDriver driver;


    @Mock
    private Consumer<IDriver> action;

    @Mock
    private WebControl control;

    @BeforeEach
    public void setUp()
    {
        command = new DoesNotHaveCommand(selector, initializer, messages, str, ComparisonOption.Text, atr);
    }

    @Test
    public void commandDelegateDoesNotHaveCommand()
    {
        // Arrange
        when(initializer.setContext()).thenReturn(action);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        // Assert
        verify(driver, times(1)).doesNotHave(control, messages, str, ComparisonOption.Text, atr);
    }

}
