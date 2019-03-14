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
class CheckNotificationDescriptionCommandTest {
    @Mock
    private IMobileDriver iMobileDriverMock;

    private CheckNotificationDescriptionCommand checkNotificationDescriptionCommand;

    @BeforeEach
    public void setUp() {
        this.checkNotificationDescriptionCommand = new CheckNotificationDescriptionCommand("description");
    }

    @Test
    public void driverDelegate_happyPath_callsRecentNotifcationDescriptionIs() {
        // Arrange

        // Act
        checkNotificationDescriptionCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).recentNotificationDescriptionIs("description");
    }
}
