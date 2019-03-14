package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class LockCommandTests {
    @Mock
    private WebControl control;
    @Mock
    private Consumer<IDriver> driverConsumer;
    @Mock
    private IMobileDriver driver;
    @Mock
    private ICommandInitializer commandInitializer;

    @Mock
    private IByWeb selector;

    private LockCommand command;

    @BeforeEach
    public void setUp() {
        command = new LockCommand();
    }

    @Test
    public void firstConstructor_IsBeingInstantiated_ShouldBeDefinedAndNotNull() {
        //Arrange
        command = new LockCommand();

        //Act

        //Assert
        assertNotNull(command);
    }

    @Test
    public void secondConstructor_IsBeingInstantiated_ShouldBeDefinedAndNotNull() {
        //Arrange
        command = new LockCommand(1);

        //Act

        //Assert
        assertNotNull(command);
    }

    @Test
    public void notVisibleCommand_CallsExecute() {
        //Arrange

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).mobileLock();
    }

    @Test
    public void driverDelegate_ZeroSecondsViaFirstConstructor_DriverShouldLockMobileNoParameters() {
        //Arrange

        //Act
        command.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).mobileLock();
    }

    @Test
    public void driverDelegate_ZeroSecondsViaSecondConstructor_DriverShouldLockMobileNoParameters() {
        //Arrange
        int lockTimeSeconds = 0;
        command = new LockCommand(lockTimeSeconds);

        //Act
        command.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).mobileLock();
        verify(driver, times(0)).mobileLock(lockTimeSeconds);
    }

    @Test
    public void driverDelegate_TwoSecondLockTime_DriverShouldLockMobileForLockTime() {
        //Arrange
        int lockTimeSeconds = 2;
        command = new LockCommand(lockTimeSeconds);

        //Act
        command.driverDelegate(driver);

        //Assert
        verify(driver, times(1)).mobileLock(lockTimeSeconds);
        verify(driver, times(0)).mobileLock();
    }
}
