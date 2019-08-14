package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;
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
class CheckNotificationDescriptionCommandTests {
    @Mock
    private IMobileDriver iMobileDriverMock;

    private CheckNotificationDescriptionCommand checkNotificationDescriptionCommand;

    @Test
    public void driverDelegate_happyPath_callsRecentNotifcationDescriptionIs() {
        // Arrange

        // Act
        this.checkNotificationDescriptionCommand = new CheckNotificationDescriptionCommand("description");
        checkNotificationDescriptionCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).recentNotificationDescriptionIs("description");
    }

    @Test
    public void driverDelegate_nullDescription_recentNotiicationDescriptionIsNull() {
        // Arrange

        // Act
        this.checkNotificationDescriptionCommand = new CheckNotificationDescriptionCommand(null);
        checkNotificationDescriptionCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).recentNotificationDescriptionIs(null);
    }
}
