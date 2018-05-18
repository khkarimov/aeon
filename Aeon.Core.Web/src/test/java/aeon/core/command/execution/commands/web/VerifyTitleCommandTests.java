package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class VerifyTitleCommandTests {

    private VerifyTitleCommand verifyTitleCommand;
    private String comparingText = "";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @BeforeEach
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

    @Test
    public void driverNullThrowsException(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> verifyTitleCommand.driverDelegate(null));
    }
}
