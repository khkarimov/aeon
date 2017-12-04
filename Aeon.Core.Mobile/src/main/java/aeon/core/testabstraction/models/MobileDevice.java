package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.mobile.*;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Provides methods for interacting with the mobile device.
 */
public class MobileDevice extends Browser {

    private AutomationInfo info;

    /**
     * The constructor for the Browser given an AutomationInfo object.
     *
     * @param info sets the info of the newly made Browser.
     */
    public MobileDevice(AutomationInfo info) {
        super(info);

        this.info = info;
    }

    /**
     * Hides the keyboard on a mobile device.
     */
    public void hideKeyboard() {
        info.getCommandExecutionFacade().execute(info, new HideKeyboardCommand());
    }

    /**
     * Sets the mobile device orientation to landscape mode.
     */
    public void setLandscape() {
        info.getCommandExecutionFacade().execute(info, new SetLandscapeCommand());
    }

    /**
     * Sets the mobile device orientation to portrait mode.
     */
    public void setPortrait() {
        info.getCommandExecutionFacade().execute(info, new SetPortraitCommand());
    }

    /**
     * Sets the GPS location on a mobile device.
     *
     * @param latitude The GPS latitude.
     * @param longitude The GPS longitude.
     * @param altitude The GPS altitude.
     */
    public void setGeoLocation(double latitude, double longitude, double altitude) {
        info.getCommandExecutionFacade().execute(info, new SetGeoLocationCommand(latitude, longitude, altitude));
    }

    /**
     * Locks and immediately unlocks a mobile device.
     */
    public void lock() {
        info.getCommandExecutionFacade().execute(info, new LockCommand());
    }

    /**
     * Locks and immediately unlocks a mobile device.
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    public void lock(int seconds){
        info.getCommandExecutionFacade().execute(info, new LockCommand(seconds));
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

    private void handlePermissionDialogIfPresent(boolean accept){
        try {
            handlePermissionDialog(accept);
        } catch (RuntimeException e) {
            // TODO(PatrickA): Use more specific exception
            // Intentionally ignore missing dialog
        }
    }

    private void handlePermissionDialog(boolean accept){
        info.getCommandExecutionFacade().execute(info, new AcceptOrDenyPermissionDialogIfPresentCommand(accept));
    }

    /**
     * Switches to the web view that contains a specific element.
     *
     * @param selector The element to look for.
     * @deprecated This might be replaced with an implicit switching logic. Please use with caution.
     */
    public void switchToWebView(IByWeb selector) {
        info.getCommandExecutionFacade().execute(info, new SwitchToWebViewCommand(selector));
    }

    /**
     * Switches to the main web view.
     *
     * @deprecated This might be replaced with an implicit switching logic. Please use with caution.
     */
    public void switchToMainWebView() {
        info.getCommandExecutionFacade().execute(info, new SwitchToWebViewCommand(null));
    }

    /**
     * Swipe screen to the left.
     */
    public void swipeLeft() {
        info.getCommandExecutionFacade().execute(info, new SwipeCommand(true, true));
    }

    /**
     * Swipe screen to the right.
     */
    public void swipeRight() {
        info.getCommandExecutionFacade().execute(info, new SwipeCommand(true, false));
    }

    /**
     * Swipe screen to the right.
     */
    public void swipeDown() {
        info.getCommandExecutionFacade().execute(info, new SwipeCommand(false, true));
    }

    /**
     * Swipe screen up.
     */
    public void swipeUp() {
        info.getCommandExecutionFacade().execute(info, new SwipeCommand(false, false));
    }
}
