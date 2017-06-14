package aeon.core.command.execution.commands.web;


import aeon.core.command.execution.commands.initialization.ICommandInitializer;
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

public class MobileSwipeCommandTests {

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

    private MobileSwipeCommand command;
    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private int duration;

    @Before
    public void setUp() {
        startx = 1;
        starty = 1;
        endx = 2;
        endy = 2;
        duration = 1;
        command = new MobileSwipeCommand(startx, starty, endx, endy, duration);
    }

    @Test
    public void MobileSwipeCommand_CallsExecute() {
        //Arrange
        when(commandInitializer.setContext()).thenReturn(driverConsumer);
        when(commandInitializer.findElement(driver, selector)).thenReturn(control);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).mobileSwipe(startx, starty, endx, endy, duration);
    }

}
