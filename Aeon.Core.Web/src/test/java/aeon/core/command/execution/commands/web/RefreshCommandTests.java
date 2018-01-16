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

public class RefreshCommandTests {
    private RefreshCommand refreshCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        refreshCommand = new RefreshCommand();
    }

    @Test
    public void commandDelegateRefreshCommand() {
        //Arrange

        //Act
        refreshCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).refresh();
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Arrange

        //Act
        refreshCommand.driverDelegate(null);

        //Assert

    }
}
