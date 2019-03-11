package aeon.core.framework.abstraction.drivers;

import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IMobileAdapter;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.testabstraction.product.Configuration;

import java.time.LocalDate;

/**
 * Mobile App framework adapter.
 */
public class AeonMobileDriver extends AeonWebDriver implements IMobileDriver {

    private IMobileAdapter adapter;

    /**
     * Initializes a new instance of the AeonWebDriver class.
     */
    public AeonMobileDriver() {
    }

    @Override
    public IDriver configure(IAdapter adapter, Configuration configuration) {
        super.configure(adapter, configuration);
        this.adapter = (IMobileAdapter) adapter;
        return this;
    }

    @Override
    public void mobileLock() {
        switch (getBrowserType().getKey()) {
            case "AndroidHybridApp":
                adapter.mobileLock();
                break;

            case "IOSHybridApp":
                adapter.mobileLock(0);
                break;
        }
    }

    /**
     * Command to lock the mobile device.
     *
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    public void mobileLock(int seconds) {
        switch (getBrowserType().getKey()) {
            case "AndroidHybridApp":
                adapter.mobileLock();
                break;

            case "IOSHybridApp":
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
    public void setDate(LocalDate date) {
        adapter.setDate(date);
    }

    @Override
    public void mobileSelect(MobileSelectOption selectOption, String value) {
        adapter.mobileSelect(selectOption, value);
    }

    @Override
    public void acceptOrDismissPermissionDialog(boolean accept) {
        adapter.acceptOrDismissPermissionDialog(accept);
    }

    @Override
    public void mobileClick(WebControl control) {
        adapter.mobileClick(control);
    }

    @Override
    public void switchToWebView(IByWeb selector) {
        adapter.switchToWebView(selector);
    }

    @Override
    public void swipe(boolean horizontally, boolean leftOrDown) {
        adapter.swipe(horizontally, leftOrDown);
    }

    @Override
    public void recentNotificationDescriptionIs(String expectedDescription) {
        adapter.recentNotificationDescriptionIs(expectedDescription);
    }

    @Override
    public void recentNotificationIs(String expectedBanner) {
        adapter.recentNotificationIs(expectedBanner);
    }

    @Override
    public void closeApp() {
        adapter.closeApp();
    }
}
