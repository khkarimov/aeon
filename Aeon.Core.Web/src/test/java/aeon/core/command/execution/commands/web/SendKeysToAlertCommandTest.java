package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SendKeysToAlertCommandTest {
    private SendKeysToAlertCommand sendKeysToAlertCommand;
    private String keysToSend = "testKey";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup() {
        sendKeysToAlertCommand = new SendKeysToAlertCommand(keysToSend);
    }

    @Test
    public void commandDelegateSendKeysToAlertCommand(){
        //Arrange

        //Act
        sendKeysToAlertCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).sendKeysToAlert(keysToSend);
    }

    @Test
    public void testDriverDelegateNullDriver(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> sendKeysToAlertCommand.driverDelegate(null));
    }
}
