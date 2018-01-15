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

public class VerifyAlertNotExistsCommandTests {
    private VerifyAlertNotExistsCommand verifyAlertNotExistsCommand;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup(){ verifyAlertNotExistsCommand = new VerifyAlertNotExistsCommand();}

    @Test
    public void commandDelegateScrollToTopCommand(){
        //Arrange

        //Act
        verifyAlertNotExistsCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).verifyAlertNotExists();
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver(){
        //Arrange

        //Act
        verifyAlertNotExistsCommand.driverDelegate(null);
        expectedException.expect(IllegalArgumentException.class);

        //Assert

    }
}
