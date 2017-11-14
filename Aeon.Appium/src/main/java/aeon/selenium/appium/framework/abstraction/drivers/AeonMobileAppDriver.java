package aeon.selenium.appium.framework.abstraction.drivers;

import aeon.core.common.exceptions.WebUsingMobileCommandException;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import aeon.selenium.appium.framework.abstraction.adapters.IMobileAppAdapter;
import org.joda.time.DateTime;

/**
 * Mobile App framework adapter.
 */
public class AeonMobileAppDriver extends AeonWebDriver implements IMobileAppDriver {

    private IMobileAppAdapter adapter;

    /**
     * Initializes a new instance of the AeonWebDriver class.
     */
    public AeonMobileAppDriver() {
    }

    @Override
    public IDriver configure(IAdapter adapter, Configuration configuration) {
        super.configure(adapter, configuration);
        this.adapter = (IMobileAppAdapter) adapter;
        return this;
    }

    @Override
    public void mobileLock() {
        switch (getBrowserType()) {
            case AndroidHybridApp:
                adapter.mobileLock();
                break;

            case IOSHybridApp:
                adapter.mobileLock(0);
                break;
        }
    }

    /**
     * Command to lock the mobile device.
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    public void mobileLock(int seconds) {
        switch (getBrowserType()) {
            case AndroidHybridApp:
                adapter.mobileLock();
                break;

            case IOSHybridApp:
                adapter.mobileLock(seconds);
                break;
        }
    }

    @Override
    public void mobileHideKeyboard() {
        adapter.mobileHideKeyboard();
    }

    @Override
    public void mobileSetLandscape() {
        adapter.mobileSetLandscape();
    }

    @Override
    public void mobileSetPortrait() {
        adapter.mobileSetPortrait();
    }

    @Override
    public void mobileSetGeoLocation(double latitude, double longitude, double altitude) {
        adapter.mobileSetGeoLocation(latitude, longitude, altitude);
    }

    @Override
    public void setDate(WebControl control, DateTime date) {
        adapter.setDate(control, date);
    }

}
