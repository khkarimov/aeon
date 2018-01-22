package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MaximizeCommandTests {
    private MaximizeCommand maximizeCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup() {
        maximizeCommand = new MaximizeCommand();
    }

    @Test
    public void commandDelegateMaximizeCommand(){
        //Arrange

        //Act
        maximizeCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).maximize();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver(){
        //Arrange

        //Act
        maximizeCommand.driverDelegate(null);

        //Assert
        expectedException.expect(IllegalArgumentException.class);
    }
}
