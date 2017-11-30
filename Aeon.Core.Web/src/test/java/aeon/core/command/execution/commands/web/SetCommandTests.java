package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.WebSelectOption;
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

public class SetCommandTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private SetCommand command;

    @Mock
    IByWeb selector;

    @Mock
    ICommandInitializer initializer;

    @Mock
    WebControl control;

    @Mock
    IWebDriver driver;

    @Mock
    Consumer<IDriver> driverConsumer;

    String value;

    @Before
    public void setUp() {
        command = new SetCommand(selector, initializer, WebSelectOption.Text, value);
    }

    @Test
    public void commandDelegateSetCommand()
    {
        //Arrange
        when(initializer.setContext()).thenReturn(driverConsumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).set(control, WebSelectOption.Text, value);
    }
}
