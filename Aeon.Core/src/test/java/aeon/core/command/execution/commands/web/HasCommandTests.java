package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IBy;
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

public class HasCommandTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> action;
    private String[] messages = {"test", "test1", "test2", "test3"};
    private String str = "teststes";
    private String atr = "testeests";
    @Mock
    private IWebDriver driver;
    @Mock
    private IBy selector;
    @Mock
    private ICommandInitializer commandInitializer;
    private HasCommand command;


    @Before
    public void setUp() {
        command = new HasCommand(selector, commandInitializer, messages, str, ComparisonOption.Raw, atr);
    }

    @Test
    public void commandDelegateHasCommand() {
        // Arrange
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).has(control, messages, str, ComparisonOption.Raw, atr);
    }

}
