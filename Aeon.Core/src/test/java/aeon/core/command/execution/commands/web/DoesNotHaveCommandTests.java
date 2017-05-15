package aeon.core.command.execution.commands.web;


import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import jdk.internal.dynalink.linker.ConversionComparator;
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

public class DoesNotHaveCommandTests {

    DoesNotHaveCommand command;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;

    @Mock
    private ICommandInitializer initializer;

    String [] messages = {"test", "test1", "test2", "test3"};

    String str = "teststes";

    String atr = "testeests";

    @Mock
    private IWebDriver driver;


    @Mock
    private Consumer<IDriver> action;

    @Mock
    private WebControl control;

    @Before
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
