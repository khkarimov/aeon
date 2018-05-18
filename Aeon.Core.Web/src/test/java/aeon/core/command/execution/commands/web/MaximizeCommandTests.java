package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MaximizeCommandTests {
    private MaximizeCommand maximizeCommand;

    @Mock
    private IWebDriver driver;

    @BeforeEach
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

    @Test
    public void testDriverDelegateNullDriver(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> maximizeCommand.driverDelegate(null));
    }
}
