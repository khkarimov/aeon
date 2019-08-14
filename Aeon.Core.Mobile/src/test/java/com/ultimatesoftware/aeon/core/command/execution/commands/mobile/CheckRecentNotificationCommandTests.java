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
class CheckRecentNotificationCommandTests {
    @Mock
    private IMobileDriver iMobileDriverMock;

    private CheckRecentNotificationCommand checkRecentNotificationCommand;

    @Test
    public void driverDelegate_happyPath_callsRecentNotifcationIs() {
        // Arrange

        // Act
        this.checkRecentNotificationCommand = new CheckRecentNotificationCommand("new notify");
        checkRecentNotificationCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).recentNotificationIs("new notify");
    }

    @Test
    public void driverDelegate_nullNotification_recentNotiicationIsNull() {
        // Arrange

        // Act
        this.checkRecentNotificationCommand = new CheckRecentNotificationCommand(null);
        checkRecentNotificationCommand.driverDelegate(iMobileDriverMock);

        // Assert
        verify(iMobileDriverMock, times(1)).recentNotificationIs(null);
    }
}
