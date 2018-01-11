package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
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

public class SwitchToMainWindowCommandTests {
    private SwitchToMainWindowCommand switchToMainWindowCommand;
    private String mainWindow = "";
    private boolean check = true;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        switchToMainWindowCommand = new SwitchToMainWindowCommand(mainWindow,check);
    }

    @Test
    public void commandDelegateSwitchToMainWindowCommand() {
        //Arrange

        //Act
        switchToMainWindowCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).switchToMainWindow(mainWindow,check);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Arrange

        //Act
        switchToMainWindowCommand.driverDelegate(null);

        //Assert

    }

}
