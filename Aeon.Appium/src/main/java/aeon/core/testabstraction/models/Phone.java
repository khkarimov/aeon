package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.mobile.*;
import aeon.core.common.exceptions.NoAlertException;

/**
 * Phone class.
 */
public class Phone extends Browser {

    private AutomationInfo info;

    /**
     * The constructor for the Browser given an AutomationInfo object.
     *
     * @param info sets the info of the newly made Browser.
     */
    public Phone(AutomationInfo info) {
        super(info);

        this.info = info;
    }

    /**
     * Hides the keyboard on a mobile device.
     */
    public void hideKeyboard() {
        info.getCommandExecutionFacade().execute(info, new MobileHideKeyboardCommand());
    }

    /**
     * Sets the mobile device orientation to landscape mode.
     */
    public void setLandscape() {
        info.getCommandExecutionFacade().execute(info, new MobileSetLandscapeCommand());
    }

    /**
     * Sets the mobile device orientation to portrait mode.
     */
    public void setPortrait() {
        info.getCommandExecutionFacade().execute(info, new MobileSetPortraitCommand());
    }

    /**
     * Sets the GPS location on a mobile device.
     *
     * @param latitude The GPS latitude.
     * @param longitude The GPS longitude.
     * @param altitude The GPS altitude.
     */
    public void setGeoLocation(double latitude, double longitude, double altitude) {
        info.getCommandExecutionFacade().execute(info, new MobileSetGeoLocationCommand(latitude, longitude, altitude));
    }

    /**
     * Locks and immediately unlocks a mobile device.
     */
    public void lock() {
        info.getCommandExecutionFacade().execute(info, new MobileLockCommand());
    }

    /**
     * Locks and immediately unlocks a mobile device.
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    public void lock(int seconds){
        info.getCommandExecutionFacade().execute(info, new MobileLockCommand(seconds));
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
        }
        catch(NoAlertException e) {
            // Intentionally ignore missing dialog
        }
    }

    private void handlePermissionDialog(boolean accept){
        info.getCommandExecutionFacade().execute(info, new AcceptOrDenyPermissionDialogIfPresentCommand(accept));
    }
}
