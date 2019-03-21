package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.*;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Provides methods for interacting with the mobile device.
 */
public class MobileDevice extends Browser {

    private AutomationInfo automationInfo;

    /**
     * The constructor for the Browser given an AutomationInfo object.
     *
     * @param automationInfo sets the automationInfo of the newly made Browser.
     */
    public MobileDevice(AutomationInfo automationInfo) {
        super(automationInfo);

        this.automationInfo = automationInfo;
    }

    /**
     * Hides the keyboard on a mobile device.
     */
    public void hideKeyboard() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new HideKeyboardCommand());
    }

    /**
     * Sets the mobile device orientation to landscape mode.
     */
    public void setLandscape() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetLandscapeCommand());
    }

    /**
     * Sets the mobile device orientation to portrait mode.
     */
    public void setPortrait() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetPortraitCommand());
    }

    /**
     * Sets the GPS location on a mobile device.
     *
     * @param latitude  The GPS latitude.
     * @param longitude The GPS longitude.
     * @param altitude  The GPS altitude.
     */
    public void setGeoLocation(double latitude, double longitude, double altitude) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SetGeoLocationCommand(latitude, longitude, altitude));
    }

    /**
     * Locks and immediately unlocks a mobile device.
     */
    public void lock() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new LockCommand());
    }

    /**
     * Locks and immediately unlocks a mobile device.
     *
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    public void lock(int seconds) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new LockCommand(seconds));
    }

    /**
     * Accepts modal for granting a permission if present.
     */
    public void acceptPermissionDialogIfPresent() {
        handlePermissionDialogIfPresent(true);
    }

    /**
     * Dismisses modal for granting a permission if present.
     */
    public void dismissPermissionDialogIfPresent() {
        handlePermissionDialogIfPresent(false);
    }

    /**
     * Accepts modal for granting a permission if present.
     */
    public void acceptPermissionDialog() {
        handlePermissionDialog(true);
    }

    /**
     * Dismisses modal for granting a permission if present.
     */
    public void dismissPermissionDialog() {
        handlePermissionDialog(false);
    }

    private void handlePermissionDialogIfPresent(boolean accept) {
        try {
            handlePermissionDialog(accept);
        } catch (RuntimeException e) {
            // Intentionally ignore missing dialog
        }
    }

    private void handlePermissionDialog(boolean accept) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new AcceptOrDenyPermissionDialogIfPresentCommand(accept));
    }

    /**
     * Switches to the web view that contains a specific element.
     *
     * @param selector The element to look for.
     * @deprecated This might be replaced with an implicit switching logic. Please use with caution.
     */
    @Deprecated
    public void switchToWebView(IByWeb selector) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWebViewCommand(selector));
    }

    /**
     * Switches to the main web view.
     *
     * @deprecated This might be replaced with an implicit switching logic. Please use with caution.
     */
    @Deprecated
    public void switchToMainWebView() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWebViewCommand(null));
    }

    /**
     * Swipe screen to the left.
     */
    public void swipeLeft() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwipeCommand(true, true));
    }

    /**
     * Swipe screen to the right.
     */
    public void swipeRight() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwipeCommand(true, false));
    }

    /**
     * Swipe screen to the right.
     */
    public void swipeDown() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwipeCommand(false, true));
    }

    /**
     * Swipe screen up.
     */
    public void swipeUp() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwipeCommand(false, false));
    }

    /**
     * Read the most recent notification's banner (what app triggered it).
     *
     * @param expectedBanner The expected app that triggered the notification.
     */
    public void recentNotificationIs(String expectedBanner) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new CheckRecentNotificationCommand(expectedBanner));
    }

    /**
     * Read and check the most recent notification's description.
     *
     * @param expectedDescription The expected description from the most recent notification.
     */
    public void recentNotificationDescriptionIs(String expectedDescription) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new CheckNotificationDescriptionCommand(expectedDescription));
    }

    /**
     * Closes current running application by pressing home key. App will stay running in the background
     * in the state that it was exited (app state will not be reset).
     */
    public void closeApp() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new CloseAppCommand());
    }
}
