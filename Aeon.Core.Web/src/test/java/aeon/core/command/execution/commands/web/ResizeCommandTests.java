package aeon.core.command.execution.commands.web;

import aeon.core.common.web.BrowserSize;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.models.Browser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ResizeCommandTests {
    private ResizeCommand resizeCommand;
    private Dimension dimensionSize;
    private BrowserSize browserSize;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        resizeCommand = new ResizeCommand(browserSize);
    }

    @Test
    public void commandDelegateResizeCommand() {
        //Arrange

        //Act
        resizeCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).resize(dimensionSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Arrange

        //Act
        resizeCommand.driverDelegate(null);

        //Assert

    }



}
