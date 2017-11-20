package aeon.core.framework.abstraction.adapters;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import com.sun.glass.ui.Size;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The interface for the Web Adapter class.
 */
public interface IMobileAppAdapter extends IWebAdapter {

    /**
     * Locks a mobile device.
     */
    void mobileLock();

    /**
     * Locks a mobile device.
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
     * Sets the mobile device's GPS location.
     * @param latitude Latitude Coordinate
     * @param longitude Longitude Coordinate
     * @param altitude Altitude
     */
    void mobileSetGeoLocation(double latitude, double longitude, double altitude);

    /**
     * Sets the date on a native date picker.
     *
     * @param date The date to set.
     */
    void setDate(DateTime date);

    void mobileSelect(MobileSelectOption selectOption, String value);

    void acceptOrDismissPermissionDialog(boolean accept, boolean ignoreMissingDialog);

    void mobileClick(WebControl control);
}
