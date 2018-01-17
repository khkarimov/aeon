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

public class VerifyAlertTextLikeCommandTests {
    private VerifyAlertTextLikeCommand verifyAlertTextLikeCommand;
    private String comparingText = "test";
    private boolean caseSensitive = true;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setup(){verifyAlertTextLikeCommand = new VerifyAlertTextLikeCommand(comparingText, caseSensitive);}

    @Test
    public void commandDelegateAlertTextLikeCommandTrue(){
        //Arrange

        //Act
        verifyAlertTextLikeCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).verifyAlertTextLike(comparingText, caseSensitive);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDriverDelegateNullDriver(){
        //Arrange

        //Act
        verifyAlertTextLikeCommand.driverDelegate(null);

        //Assert
        expectedException.expect(IllegalArgumentException.class);
    }
}
