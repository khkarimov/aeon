package aeon.core.command.execution.commands;

import aeon.core.framework.abstraction.drivers.IDriver;
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
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class QuitCommandTest {
    private QuitCommand quitCommand;

    @Mock
    private IDriver driver;

    @BeforeEach
    public void setUp() {
        quitCommand = new QuitCommand();
    }

    @Test
    public void testDriverDelegate() {
        //Arrange

        //Act
        quitCommand.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).quit();
    }

    @Test
    public void testDriverDelegateNullDriver() {
        //Arrange

        //Act

        //Assert
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> quitCommand.driverDelegate(null));
    }

}
