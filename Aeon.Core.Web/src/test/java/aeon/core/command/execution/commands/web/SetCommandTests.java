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
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SetCommandTests {

    private SetCommand setCommandObject;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        setCommandObject = new SetCommand(selector, initializer, WebSelectOption.Text, value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void commandDelegateDriverNullThrowsException()
    {
        //Arrange

        //Act
        setCommandObject.commandDelegate(null, control);

        //Assert
        thrown.expectMessage("driver");

    }

    @Test
    public void commandDelegateSetCommand()
    {
        //Arrange
        when(initializer.setContext()).thenReturn(driverConsumer);
        when(initializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = setCommandObject.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).set(control, WebSelectOption.Text, value);
    }
}
