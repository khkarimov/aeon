package aeon.selenium.appium.framework.abstraction.drivers;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import com.sun.glass.ui.Size;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The framework adapter interface.
 */
public interface IMobileAppDriver extends IWebDriver {

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
     * @param latitude The GPS latitude.
     * @param longitude The GPS longitude.
     * @param altitude The GPS altitude.
     */
    void mobileSetGeoLocation(double latitude, double longitude, double altitude);

    /**
     * Sets the date on a native date picker.
     *
     * @param control The date control.
     * @param date The date to set.
     */
    void setDate(WebControl control, DateTime date);
}
