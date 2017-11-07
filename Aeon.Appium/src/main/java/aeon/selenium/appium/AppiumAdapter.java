package aeon.selenium.appium;

import aeon.core.common.web.BrowserType;
import aeon.selenium.SeleniumAdapter;
import aeon.selenium.jquery.IJavaScriptFlowExecutor;
import framework.abstraction.adapters.IMobileAppAdapter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.Location;

import java.time.Duration;

/**
 * Web adapter for Appium.
 */
public class AppiumAdapter extends SeleniumAdapter implements IMobileAppAdapter {

    private static Logger log = LogManager.getLogger(AppiumAdapter.class);

    /**
     * Constructor for Selenium Adapter.
     * @param seleniumWebDriver The driver for the adapter.
     * @param javaScriptExecutor The javaScript executor for the adapter.
     * @param moveMouseToOrigin A boolean indicating whether or not the mouse will return to the origin
     *                          (top left corner of the browser window) before executing every action.
     * @param browserType The browser type for the adapter.
     * @param isRemote Whether we are testing remotely or locally.
     */
    public AppiumAdapter(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, boolean moveMouseToOrigin, BrowserType browserType, boolean isRemote) {
        super(seleniumWebDriver, javaScriptExecutor, moveMouseToOrigin, browserType, isRemote);
    }

    /**
     * Gets the web driver.
     * @return The web driver is returned.
     */
    protected AppiumDriver getMobileWebDriver() {
        if(!(webDriver instanceof AppiumDriver)){
            throw new RuntimeException("This command is only supported by an AppiumDriver");
        }

        return (AppiumDriver) webDriver;
    }

    @Override
    public void mobileSetPortrait() {
        getMobileWebDriver().rotate(ScreenOrientation.PORTRAIT);
    }

    @Override
    public void mobileSetLandscape() {
        getMobileWebDriver().rotate(ScreenOrientation.LANDSCAPE);
    }

    @Override
    public void mobileHideKeyboard() {
        getMobileWebDriver().hideKeyboard();
    }

    @Override
    public void mobileLock() {
        mobileLock(0);
    }

    @Override
    public void mobileLock(int seconds) {
        switch (browserType) {
            case AndroidHybridApp:
                ((AndroidDriver) getMobileWebDriver()).lockDevice();
                break;
            case IOSHybridApp:
                ((IOSDriver) getMobileWebDriver()).lockDevice(Duration.ofSeconds(seconds));
                break;
            default:
                throw new RuntimeException(String.format(
                        "Aeon.Appium does not support the browser type \"%s\".",
                        browserType));
        }
    }

    @Override
    public void mobileSetGeoLocation(double latitude, double longitude, double altitude) {
        getMobileWebDriver().setLocation(new Location(latitude, longitude, altitude));
    }
}
