package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IMobileDriver;
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
class CloseAppCommandTest {
    private CloseAppCommand closeAppCommand;

    @Mock
    private IMobileDriver driverMock;

    @BeforeEach
    public void setUp() {
        closeAppCommand = new CloseAppCommand();
    }

    @Test
    public void driverDelegate_happyPath_callsCloseApp() {
        // Arrange

        // Act
        closeAppCommand.driverDelegate(driverMock);

        // Assert
        verify(driverMock, times(1)).closeApp();
    }
}
