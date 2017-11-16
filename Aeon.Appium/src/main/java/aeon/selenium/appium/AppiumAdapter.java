package aeon.selenium.appium;

import aeon.core.common.web.BrowserType;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.common.mobile.interfaces.INativeBy;
import aeon.core.common.mobile.interfaces.INativeByXPath;
import aeon.core.common.mobile.selectors.NativeBy;
import aeon.core.common.mobile.selectors.NativeById;
import aeon.selenium.appium.jquery.IJavaScriptFlowExecutor;
import aeon.core.framework.abstraction.adapters.IMobileAppAdapter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
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

    @Override
    public void setDate(WebControl control, DateTime date) {
        tapOnControl(control);

        if(browserType == BrowserType.AndroidHybridApp){
            WebControl label = findElement(NativeBy.accessibilityId(date.toString("dd MMMM yyyy")));
            click(label, false);
            WebControl label1 = findElement(NativeBy.id("android:id/button1"));

            click(label1, false);
        }
        else {
            WebControl day = findElement(NativeBy.xpath("//XCUIElementTypePickerWheel[1]"));
            String context = getMobileWebDriver().getContext();
            getMobileWebDriver().context("NATIVE_APP");
            ((SeleniumElement) day).getUnderlyingWebElement().sendKeys("December");
            getMobileWebDriver().context(context);
            //day.se
        }
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    public WebControl findElement(IBy findBy) {
        if(findBy instanceof INativeByXPath){
            String context = getMobileWebDriver().getContext();
            log.trace(String.format("WebDriver.findElement(by.xpath(%1$s));", findBy));
            try {
                getMobileWebDriver().context("NATIVE_APP");
                SeleniumElement element =  new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.xpath(findBy.toString())));
                getMobileWebDriver().context(context);

                return element;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            }
        }

        if(findBy instanceof NativeById){
            log.trace(String.format("WebDriver.findElement(by.id(%1$s));", findBy));
            try {
                getMobileWebDriver().context("NATIVE_APP");
                SeleniumElement element =  new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.id(findBy.toString())));
                getMobileWebDriver().context("WEBVIEW_1");

                return element;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            }
        }

        if(findBy instanceof NativeBy){
            log.trace(String.format("WebDriver.findElement(by.accessbilityId(%1$s));", findBy));
            try {
                getMobileWebDriver().context("NATIVE_APP");
                SeleniumElement element = new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.name(findBy.toString())));
                getMobileWebDriver().context("WEBVIEW_1");

                return element;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            }
        }

        return super.findElement(findBy);
    }

    /**
     * Performs a LeftClick on the element passed as an argument.
     *
     * @param element The element to perform the click on.
     */
    public final void click(WebControl element) {
        if (element.getSelector() instanceof INativeBy) {
            click(element, false);

            return;
        }

        super.click(element);
    }

    private void tapOnControl(WebControl control){

        log.trace("tap on control via coordinates");

        WebElement underlyingWebElement = ((SeleniumElement) control).getUnderlyingWebElement();
        Point webElementLocation = underlyingWebElement.getLocation();
        log.trace("elementLocation: " + webElementLocation.getX() + "," + webElementLocation.getY());
        Dimension elementSize = underlyingWebElement.getSize();
        log.trace("elementSize: " + elementSize.getWidth() + "," + elementSize.getHeight());
        Dimension windowSize = getWebDriver().manage().window().getSize();
        log.trace("windowSize: " + windowSize.getWidth() + "," + windowSize.getHeight());

        long webRootWidth = (long) executeScript("return screen.availWidth");
        long webRootHeight = (long) executeScript("return screen.availHeight");
        log.trace("screenSize: " + webRootWidth + "," + webRootHeight);

        double xRatio = (double) (windowSize.getWidth() * 1.0 / webRootWidth);
        double yRatio = (double) (windowSize.getHeight() * 1.0 / webRootHeight);
        int pointX = (int) ((webElementLocation.getX() + elementSize.getWidth() / 2.0));
        int pointY = (int) ((webElementLocation.getY() + elementSize.getHeight() / 2.0));
        Point tapPoint = new Point((int) (pointX * xRatio), (int) (pointY * yRatio));

        log.trace("tapPoint: " + tapPoint.getX() + "," + tapPoint.getY());
        TouchAction a = new TouchAction((AppiumDriver)getWebDriver());
        //a2.tap(((SeleniumElement) element).getUnderlyingWebElement()).perform();
        a.tap(tapPoint.getX(), tapPoint.getY()).perform();
    }
}
