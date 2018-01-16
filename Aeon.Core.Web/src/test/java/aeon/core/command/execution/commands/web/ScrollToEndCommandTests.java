package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ScrollToEndCommandTests {
    private ScrollToEndCommand scrollToEndCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() { scrollToEndCommand = new ScrollToEndCommand(); }

    @Test
    public void commandDelegateScrollToEndCommand() {
        //Arrange

        //Act
        scrollToEndCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).scrollToEnd();
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Arrange

        //Act
        scrollToEndCommand.driverDelegate(null);

        //Assert

    }
}
