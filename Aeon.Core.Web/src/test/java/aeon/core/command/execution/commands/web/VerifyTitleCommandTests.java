package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VerifyTitleCommandTests {

    private VerifyTitleCommand verifyTitleCommand;
    private String comparingText = "";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Before
    public void setUp() {
        verifyTitleCommand = new VerifyTitleCommand(comparingText);
    }

    @Test
    public void commandDelegateVerifyTitleCommand() {
        //Arrange

        //Act
        verifyTitleCommand.driverDelegate(driver);

        //Assert
        verify(driver,times(1)).verifyTitle(comparingText);
    }

    @Test(expected = IllegalArgumentException.class)
    public void driverNullThrowsException(){
        //Arrange

        //Act
        verifyTitleCommand.driverDelegate(null);

        //Assert
        expectedException.expect(IllegalArgumentException.class);
    }
}
