package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
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

public class HasOptionsInOrderCommandTests {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    WebControl control;
    @Mock
    Consumer<IDriver> action;
    private String[] options = {"test", "test1", "test2", "test3"};
    private String optGroup = "teststes";
    @Mock
    private IWebDriver driver;
    @Mock
    private IBy selector;

    @Mock
    private ICommandInitializer commandInitializer;
    private HasOptionsInOrderCommand command;


    @Before
    public void setUp() {
        command = new HasOptionsInOrderCommand(selector, commandInitializer, options, optGroup, WebSelectOption.Text);
    }

    @Test
    public void commandDelegateHasOptionsCommand() {
        // Arrange
        when(commandInitializer.setContext()).thenReturn(action);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        // Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).hasOptionsInOrder(control, options, optGroup, WebSelectOption.Text);
    }

}
