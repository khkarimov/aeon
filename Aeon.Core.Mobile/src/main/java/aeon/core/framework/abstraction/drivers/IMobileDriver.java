package aeon.core.framework.abstraction.drivers;

import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;

import java.time.LocalDate;

/**
 * The framework adapter interface.
 */
public interface IMobileDriver extends IWebDriver {

    /**
     * Locks and immediately unlocks a mobile device.
     */
    void mobileLock();

    /**
     * Locks and immediately unlocks a mobile device.
     *
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    void mobileLock(int seconds);

    /**
     * Hides the keyboard on a mobile device.
     */
    void mobileHideKeyboard();

    /**
     * Sets the mobile device's orientation to landscape.
     */
    void mobileSetLandscape();

    /**
     * Sets the mobile device's orientation to portrait.
     */
    void mobileSetPortrait();

    /**
     * Sets the GPS location on a mobile device.
     *
     * @param latitude  The GPS latitude.
     * @param longitude The GPS longitude.
     * @param altitude  The GPS altitude.
     */
    void mobileSetGeoLocation(double latitude, double longitude, double altitude);

    /**
     * Sets the date on a native date picker.
     *
     * @param date The date to set.
     */
    void setDate(LocalDate date);

    /**
     * Selects an option from a native select box.
     *
     * @param selectOption Type based on which an option should be selected.
     * @param value        The value to select.
     */
    void mobileSelect(MobileSelectOption selectOption, String value);

    /**
     * Accepts or dismisses a modal dialog asking for a permission.
     *
     * @param accept Whether to accept of deny the permission.
     */
    void acceptOrDismissPermissionDialog(boolean accept);

    /**
     * Click on an element via a native tap (using coordinates).
     *
     * @param control The element to click on.
     */
    void mobileClick(WebControl control);

    /**
     * Switch to a web view that contains the specified element.
     *
     * @param selector Selector of the element to look for.
     */
    void switchToWebView(IByWeb selector);

    /**
     * Swipe screen horizontally or vertically.
     *
     * @param horizontally Whether to swipe horizontally or vertically.
     * @param leftOrDown   Whether to swipe left (when horizontally is true) or down (when horizontally is false).
     */
    void swipe(boolean horizontally, boolean leftOrDown);

    /**
     * Read and check the most recent notification's description.
     *
     * @param expectedDescription The expected description from the most recent notification.
     */
    void recentNotificationDescriptionIs(String expectedDescription);

    /**
     * Read the most recent notification's banner (what app triggered it).
     *
     * @param expectedBanner The expected app that triggered the notification.
     */
    void recentNotificationIs(String expectedBanner);

    /**
     * Closes current running application by pressing home key. App will stay running in the background
     * in the state that it was exited (app state will not be reset).
     */
    void closeApp();
}
