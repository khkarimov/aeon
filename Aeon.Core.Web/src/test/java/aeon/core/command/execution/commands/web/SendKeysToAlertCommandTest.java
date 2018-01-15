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

public class SendKeysToAlertCommandTest {
    private SendKeysToAlertCommand sendKeysToAlertCommand;
    private String keysToSend = "testKey";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup(){ sendKeysToAlertCommand = new SendKeysToAlertCommand(keysToSend);}

    @Test
    public void commandDelegateSendKeysToAlertCommand(){
        //Arrange

        //Act
        sendKeysToAlertCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).sendKeysToAlert(keysToSend);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver(){
        //Arrange

        //Act
        sendKeysToAlertCommand.driverDelegate(null);
        expectedException.expect(IllegalArgumentException.class);

        //Assert

    }
}
