package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.mobile.*;
import aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class MobileDeviceTests {

    private MobileDevice mobileDevice;

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private ICommandExecutionFacade executionFacade;

    @Mock
    private IByWeb selectorMock;

    @BeforeEach()
    void beforeEach() {
        when(automationInfo.getCommandExecutionFacade()).thenReturn(executionFacade);
        mobileDevice = new MobileDevice(automationInfo);
    }

    @Test
    void setLandscape_called_dispatchSetLandscapeCommand() {
        // Arrange

        // Act
        mobileDevice.setLandscape();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SetLandscapeCommand.class));
    }

    @Test
    void setPortrait_called_dispatchSetPortraitCommand() {
        // Arrange

        // Act
        mobileDevice.setPortrait();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SetPortraitCommand.class));
    }

    @Test
    void lock_called_dispatchLockCommand() {
        // Arrange

        // Act
        mobileDevice.lock();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(LockCommand.class));
    }

    @Test
    void hideKeyboard_called_dispatchHideKeyboardCommand() {
        // Arrange

        // Act
        mobileDevice.hideKeyboard();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(HideKeyboardCommand.class));
    }

    @Test
    void closeApp_called_dispatchCloseAppCommand() {
        // Arrange

        // Act
        mobileDevice.closeApp();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(CloseAppCommand.class));
    }

    @Test
    void setGeoLocation_called_dispatchSetGeoLocationCommand() {
        // Arrange

        // Act
        mobileDevice.setGeoLocation(0, 0, 0);

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SetGeoLocationCommand.class));
    }

    @Test
    void lockWithArgument_called_dispatchLockCommand() {
        // Arrange

        // Act
        mobileDevice.lock(0);

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(LockCommand.class));
    }

    @Test
    void acceptPermissionDialogIfPresent_called_dispatchAcceptOrDenyPermissionDialogIfPresentCommand() {
        // Arrange

        // Act
        mobileDevice.acceptPermissionDialogIfPresent();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(AcceptOrDenyPermissionDialogIfPresentCommand.class));
    }

    @Test
    void dismissPermissionDialogIfPresent_called_dispatchAcceptOrDenyPermissionDialogIfPresentCommand() {
        // Arrange

        // Act
        mobileDevice.dismissPermissionDialogIfPresent();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(AcceptOrDenyPermissionDialogIfPresentCommand.class));
    }

    @Test
    void acceptPermissionDialog_called_dispatchAcceptOrDenyPermissionDialogIfPresentCommand() {
        // Arrange

        // Act
        mobileDevice.acceptPermissionDialog();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(AcceptOrDenyPermissionDialogIfPresentCommand.class));
    }

    @Test
    void dismissPermissionDialog_called_dispatchAcceptOrDenyPermissionDialogIfPresentCommand() {
        // Arrange

        // Act
        mobileDevice.dismissPermissionDialog();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(AcceptOrDenyPermissionDialogIfPresentCommand.class));
    }

    @Test
    void switchToWebView_called_dispatchSwitchToWebViewCommand() {
        // Arrange

        // Act
        mobileDevice.switchToWebView(selectorMock);

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwitchToWebViewCommand.class));
    }

    @Test
    void switchToMainWebView_called_dispatchSwitchToWebViewCommand() {
        // Arrange

        // Act
        mobileDevice.switchToMainWebView();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwitchToWebViewCommand.class));
    }

    @Test
    void swipeLeft_called_dispatchSwipeCommand() {
        // Arrange

        // Act
        mobileDevice.swipeLeft();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwipeCommand.class));
    }

    @Test
    void swipeRight_called_dispatchSwipeCommand() {
        // Arrange

        // Act
        mobileDevice.swipeRight();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwipeCommand.class));
    }

    @Test
    void swipeUp_called_dispatchSwipeCommand() {
        // Arrange

        // Act
        mobileDevice.swipeUp();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwipeCommand.class));
    }

    @Test
    void swipeDown_called_dispatchSwipeCommand() {
        // Arrange

        // Act
        mobileDevice.swipeDown();

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(SwipeCommand.class));
    }

    @Test
    void recentNotificationIs_called_dispatchCheckRecentNotificationCommand() {
        // Arrange

        // Act
        mobileDevice.recentNotificationIs("");

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(CheckRecentNotificationCommand.class));
    }

    @Test
    void recentNotificationDescriptionIs_called_dispatchCheckRecentNotificationCommand() {
        // Arrange

        // Act
        mobileDevice.recentNotificationDescriptionIs("");

        // Assert
        verify(executionFacade, times(1)).execute(any(AutomationInfo.class), any(CheckNotificationDescriptionCommand.class));
    }
}
