package aeon.core.command.execution.commands.web;


import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.mobile.MobileLockCommand;
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

public class MobileLockCommandTests {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    WebControl control;
    @Mock
    private Consumer<IDriver> driverConsumer;
    @Mock
    private IWebDriver driver;
    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IBy selector;

    private MobileLockCommand command;

    @Before
    public void setUp() {
        command = new MobileLockCommand();
    }

    @Test
    public void notVisibleCommand_CallsExecute() {
        //Arrange
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).mobileLock();
    }
}
