package aeon.selenium.appium;

import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.JQueryStringType;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.common.mobile.interfaces.INativeBy;
import aeon.core.common.mobile.interfaces.INativeByXPath;
import aeon.core.common.mobile.selectors.NativeBy;
import aeon.core.common.mobile.selectors.NativeById;
import aeon.selenium.SeleniumAdapter;
import aeon.selenium.SeleniumElement;
import aeon.selenium.jquery.IJavaScriptFlowExecutor;
import aeon.core.framework.abstraction.adapters.IMobileAppAdapter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;

import java.time.Duration;
import java.util.HashMap;

/**
 * Web adapter for Appium.
 */
public class AppiumAdapter extends SeleniumAdapter implements IMobileAppAdapter {

    private static Logger log = LogManager.getLogger(AppiumAdapter.class);

    private String context;

    private HashMap<Integer, Integer> phoneResolutions = new HashMap<>();

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

        if(browserType == BrowserType.AndroidHybridApp || browserType == BrowserType.IOSHybridApp) {
            context = getMobileWebDriver().getContext();
        }

        phoneResolutions.put(1125, 2436);
        phoneResolutions.put(1080, 1920);
        phoneResolutions.put(750, 1334);
        phoneResolutions.put(640, 1136);
        phoneResolutions.put(2048, 2732);
        phoneResolutions.put(1536, 2048);
        phoneResolutions.put(768, 1024);
        phoneResolutions.put(1440, 2560);
        phoneResolutions.put(1200, 1920);
        phoneResolutions.put(800, 1280);
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
    public void setDate(DateTime date) {

        if(browserType == BrowserType.AndroidHybridApp){
            switchToNativeAppContext();
            WebControl label = findElement(NativeBy.accessibilityId(date.toString("dd MMMM yyyy")), false);
            click(label, false);
            WebControl label1 = findElement(NativeBy.id("android:id/button1"), false);
            click(label1, false);
            switchToWebViewContext();
        }
        else {
            switchToNativeAppContext();
            WebControl month = findElement(NativeBy.xpath("//XCUIElementTypePickerWheel[1]"), false);
            ((SeleniumElement) month).getUnderlyingWebElement().sendKeys(date.toString("MMMM"));
            WebControl day = findElement(NativeBy.xpath("//XCUIElementTypePickerWheel[2]"), false);
            ((SeleniumElement) day).getUnderlyingWebElement().sendKeys(date.toString("dd"));
            WebControl year = findElement(NativeBy.xpath("//XCUIElementTypePickerWheel[3]"), false);
            ((SeleniumElement) year).getUnderlyingWebElement().sendKeys(date.toString("yyyy"));
            switchToWebViewContext();
        }
    }

    @Override
    public void mobileSelect(MobileSelectOption selectOption, String value) {
        if(browserType == BrowserType.AndroidHybridApp){
            switchToNativeAppContext();
            INativeBy selector = NativeBy.xpath(String.format("//android.widget.CheckedTextView[@text='%s']", value));
            click(findElement(selector, false), false);
            switchToWebViewContext();
        }
        else {
            switchToNativeAppContext();
            click(findElement(NativeBy.accessibilityId("Done"), false), false);
            switchToWebViewContext();
        }
    }

    /**
     * Scrolls the element specified by the provided 'selector' into view.
     *
     * @param selector Element to scroll into view.
     */
    protected void scrollElementIntoView(IBy selector) {
        if(selector instanceof INativeBy){
            return;
        }

        super.scrollElementIntoView(selector);
    }

    /**
     * Accepts an alert on the page.
     */
    public void acceptAlert() {
        switch (browserType){
            case AndroidHybridApp:
                // Break intentionally omitted
            case IOSHybridApp:
                switchToNativeAppContext();
                try {
                    super.acceptAlert();
                }
                finally {
                    switchToWebViewContext();
                }
                break;
            default:
                super.acceptAlert();
        }
    }

    @Override
    public void acceptOrDismissPermissionDialog(boolean accept, boolean ignoreMissingDialog) {
        if(accept){
            if(browserType == BrowserType.AndroidHybridApp) {
                SeleniumElement element = (SeleniumElement) findElement(NativeBy.id("com.android.packageinstaller:id/permission_allow_button"));
                element.click(false);
            }
            else {
                acceptAlert();
                //switchToNativeAppContext();
                //SeleniumElement element = (SeleniumElement) findElement(NativeBy.accessibilityId("Allow"));
                //switchToNativeAppContext();
                //element.click(false);
                //switchToWebViewContext();
            }
        }
    }

    @Override
    public void mobileClick(WebControl control) {
        tapOnControl(control);
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    public WebControl findElement(IBy findBy) {
        return findElement(findBy, true);
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    private WebControl findElement(IBy findBy, boolean switchContext) {
        if(!(findBy instanceof INativeBy)){
            return super.findElement(findBy);
        }

        if(switchContext){
            switchToNativeAppContext();
        }

        if(findBy instanceof INativeByXPath){
            log.trace(String.format("WebDriver.findElement(by.xpath(%1$s));", findBy));
            try {
                SeleniumElement element =  new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.xpath(findBy.toString())));

                return element;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            }
            finally {
                if(switchContext){
                    switchToWebViewContext();
                }
            }
        }

        if(findBy instanceof NativeById){
            log.trace(String.format("WebDriver.findElement(by.id(%1$s));", findBy));
            try {
                SeleniumElement element =  new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.id(findBy.toString())));

                return element;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            }
            finally {
                if(switchContext){
                    switchToWebViewContext();
                }
            }
        }

        log.trace(String.format("WebDriver.findElement(by.accessbilityId(%1$s));", findBy));
        try {
            SeleniumElement element = new SeleniumElement(((MobileDriver )webDriver).findElementByAccessibilityId(findBy.toString()));

            return element;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
        }
        finally {
            if(switchContext){
                switchToWebViewContext();
            }
        }
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

    private void switchToNativeAppContext() {
        log.trace("Switching to native app context " + "NATIVE_APP");
        getMobileWebDriver().context("NATIVE_APP");
    }

    private void switchToWebViewContext() {
        log.trace("Switching to web view context " + context);
        getMobileWebDriver().context(context);
    }

    private void tapOnControl(WebControl control){

        log.trace("tap on control via coordinates");

        WebElement underlyingWebElement = ((SeleniumElement) control).getUnderlyingWebElement();
        Point webElementLocation = underlyingWebElement.getLocation();
        log.trace("elementLocation: " + webElementLocation.getX() + "," + webElementLocation.getY());
        Dimension elementSize = underlyingWebElement.getSize();
        log.trace("elementSize: " + elementSize.getWidth() + "," + elementSize.getHeight());

        long webRootWidth = (long) executeScript("return screen.availWidth");
        long webRootHeight = (long) executeScript("return screen.availHeight");
        log.trace("screenSize: " + webRootWidth + "," + webRootHeight);

        int windowWidth;
        int windowHeight;
        try {
            switchToNativeAppContext();
            Dimension windowSize = getMobileWebDriver().manage().window().getSize();
            switchToWebViewContext();
            windowWidth = windowSize.getWidth();
            windowHeight = windowSize.getHeight();

            if(browserType == BrowserType.AndroidHybridApp && phoneResolutions.containsKey(windowWidth)) {
                windowHeight = phoneResolutions.get(windowWidth);
            }
        }
        catch(WebDriverException e){
            log.trace(e.getMessage());
            windowWidth = (int) webRootWidth;
            windowHeight = 1920;//(int) webRootHeight;
        }

        log.trace("windowSize: " + windowWidth + "," + windowHeight);

        double xRatio = (double) (windowWidth * 1.0 / webRootWidth);
        double yRatio = (double) (windowHeight * 1.0 / webRootHeight);
        int pointX = (int) ((webElementLocation.getX() + elementSize.getWidth() / 2.0));
        int pointY = (int) ((webElementLocation.getY() + elementSize.getHeight() / 2.0));
        Point tapPoint = new Point((int) (pointX * xRatio), (int) (pointY * yRatio));

        log.trace("tapPoint: " + tapPoint.getX() + "," + tapPoint.getY());
        switchToNativeAppContext();
        TouchAction a = new TouchAction((AppiumDriver)getWebDriver());
        //a2.tap(((SeleniumElement) element).getUnderlyingWebElement()).perform();
        a.tap(tapPoint.getX(), tapPoint.getY()).perform();
        switchToWebViewContext();
    }
}
