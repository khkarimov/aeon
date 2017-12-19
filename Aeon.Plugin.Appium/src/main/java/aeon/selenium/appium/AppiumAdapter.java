package aeon.selenium.appium;

import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.exceptions.NoSuchElementsException;
import aeon.core.common.mobile.interfaces.IByMobile;
import aeon.core.common.mobile.interfaces.IByMobileXPath;
import aeon.core.common.mobile.selectors.ByMobile;
import aeon.core.common.mobile.selectors.ByMobileId;
import aeon.core.common.mobile.selectors.MobileSelectOption;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IMobileAdapter;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.selenium.SeleniumAdapter;
import aeon.selenium.SeleniumElement;
import aeon.selenium.jquery.IJavaScriptFlowExecutor;
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
import java.util.Set;

/**
 * Mobile adapter for Appium.
 */
public class AppiumAdapter extends SeleniumAdapter implements IMobileAdapter {

    private static Logger log = LogManager.getLogger(AppiumAdapter.class);

    private String context;

    private HashMap<Integer, Integer> mobileDeviceResolutions = new HashMap<>();

    /**
     * Constructor for Selenium Adapter.
     *
     * @param seleniumWebDriver The driver for the adapter.
     * @param javaScriptExecutor The javaScript executor for the adapter.
     * @param moveMouseToOrigin A boolean indicating whether or not the mouse will return to the origin
     *                          (top left corner of the browser window) before executing every action.
     * @param browserType The browser type for the adapter.
     * @param isRemote Whether we are testing remotely or locally.
     */
    public AppiumAdapter(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, boolean moveMouseToOrigin, BrowserType browserType, boolean isRemote) {
        super(seleniumWebDriver, javaScriptExecutor, moveMouseToOrigin, browserType, isRemote);

        if (browserType == BrowserType.AndroidHybridApp || browserType == BrowserType.IOSHybridApp) {
            context = getMobileWebDriver().getContext();
        }

        mobileDeviceResolutions.put(1125, 2436);
        mobileDeviceResolutions.put(1080, 1920);
        mobileDeviceResolutions.put(750, 1334);
        mobileDeviceResolutions.put(640, 1136);
        mobileDeviceResolutions.put(2048, 2732);
        mobileDeviceResolutions.put(1536, 2048);
        mobileDeviceResolutions.put(768, 1024);
        mobileDeviceResolutions.put(1440, 2560);
        mobileDeviceResolutions.put(1200, 1920);
        mobileDeviceResolutions.put(800, 1280);
    }

    /**
     * Gets the web driver.
     * @return The web driver is returned.
     */
    protected AppiumDriver getMobileWebDriver() {
        if (!(webDriver instanceof AppiumDriver)){
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
    public void swipe(boolean horizontally, boolean leftOrDown) {
        switchToNativeAppContext();
        Dimension screenSize = getMobileWebDriver().manage().window().getSize();

        int width = screenSize.getWidth();
        int height = screenSize.getHeight();

        log.trace("Screen size: " + width + ", " + height);

        int startX = (int) (width * 0.78);
        int startY = height / 2;
        if (!leftOrDown) {
            startX = (int) (width * 0.22);
        }

        if (!horizontally) {
            startX = width / 2;
            startY = (int) (height * 0.25);

            if (!leftOrDown) {
                startY = (int) (height * 0.75);
            }
        }

        log.trace("Swipe start point: " + startX + ", " + startY);

        TouchAction action = new TouchAction(getMobileWebDriver());
        action.press(startX, startY)
                .moveTo(width - startX * 2, height - startY * 2)
                .release()
                .perform();

        switchToWebViewContext();
    }

    @Override
    public void setDate(DateTime date) {

        if (browserType == BrowserType.AndroidHybridApp){
            switchToNativeAppContext();
            WebControl label = findElement(ByMobile.accessibilityId(date.toString("dd MMMM yyyy")), false);
            click(label, false);
            WebControl label1 = findElement(ByMobile.id("android:id/button1"), false);
            click(label1, false);
            switchToWebViewContext();
        } else {
            switchToNativeAppContext();
            WebControl month = findElement(ByMobile.xpath("//XCUIElementTypePickerWheel[1]"), false);
            ((SeleniumElement) month).getUnderlyingWebElement().sendKeys(date.toString("MMMM"));
            WebControl day = findElement(ByMobile.xpath("//XCUIElementTypePickerWheel[2]"), false);
            ((SeleniumElement) day).getUnderlyingWebElement().sendKeys(date.toString("dd"));
            WebControl year = findElement(ByMobile.xpath("//XCUIElementTypePickerWheel[3]"), false);
            ((SeleniumElement) year).getUnderlyingWebElement().sendKeys(date.toString("yyyy"));
            switchToWebViewContext();
        }
    }

    @Override
    public void mobileSelect(MobileSelectOption selectOption, String value) {
        if (browserType == BrowserType.AndroidHybridApp){
            switchToNativeAppContext();
            IByMobile selector = ByMobile.xpath(String.format("//android.widget.CheckedTextView[@text='%s']", value));
            click(findElement(selector, false), false);
            switchToWebViewContext();
        } else {
            switchToNativeAppContext();
            WebControl element = findElement(ByMobile.xpath("//XCUIElementTypePickerWheel[1]"), false);
            ((SeleniumElement) element).getUnderlyingWebElement().sendKeys(value);
            click(findElement(ByMobile.accessibilityId("Done"), false), false);
            switchToWebViewContext();
        }
    }

    /**
     * Scrolls the element specified by the provided 'selector' into view.
     *
     * @param selector Element to scroll into view.
     */
    protected void scrollElementIntoView(IByWeb selector) {
        if (selector instanceof IByMobile){
            return;
        }

        super.scrollElementIntoView(selector);
    }

    @Override
    public void acceptAlert() {
        switch (browserType){
            case AndroidHybridApp:
                // Break intentionally omitted
            case IOSHybridApp:
                switchToNativeAppContext();
                try {
                    super.acceptAlert();
                } finally {
                    switchToWebViewContext();
                }
                break;
            default:
                super.acceptAlert();
        }
    }

    @Override
    public void dismissAlert() {
        switch (browserType){
            case AndroidHybridApp:
                // Break intentionally omitted
            case IOSHybridApp:
                switchToNativeAppContext();
                try {
                    super.dismissAlert();
                } finally {
                    switchToWebViewContext();
                }
                break;
            default:
                super.dismissAlert();
        }
    }

    @Override
    public void acceptOrDismissPermissionDialog(boolean accept) {
        if (accept) {
            if (browserType == BrowserType.AndroidHybridApp) {
                SeleniumElement element = (SeleniumElement) findElement(ByMobile.id("com.android.packageinstaller:id/permission_allow_button"));
                element.click(false);
            } else {
                acceptAlert();
            }
        } else {
            if (browserType == BrowserType.AndroidHybridApp) {
                SeleniumElement element = (SeleniumElement) findElement(ByMobile.id("com.android.packageinstaller:id/permission_deny_button"));
                element.click(false);
            } else {
                dismissAlert();
            }
        }
    }

    @Override
    public void mobileClick(WebControl control) {
        tapOnControl(control);
    }

    @Override
    public void switchToWebView(IByWeb selector) {

        if (selector == null) {
            switchToWebViewContext();

            return;
        }

        log.trace("switchToWebView(" + selector + ")");
        Set<String> availableContexts = getMobileWebDriver().getContextHandles();
        log.trace("Available contexts: " + String.join(", ", availableContexts));
        if (availableContexts.size() > 1) {
            for (String context : availableContexts) {
                if (!context.contains("NATIVE_APP") && context.startsWith("WEBVIEW")) {
                    log.trace("Switching to context " + context);
                    getMobileWebDriver().context(context);
                    log.trace("Switched to context");

                    try {
                        findElement(selector);
                    } catch (NoSuchElementException | NoSuchElementsException e) {
                        log.trace(e.getMessage());

                        continue;
                    }

                    return;
                }
            }
        }

        throw new RuntimeException("Unable to find matching web view");
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    public WebControl findElement(IByWeb findBy) {
        return findElement(findBy, true);
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    private WebControl findElement(IByWeb findBy, boolean switchContext) {
        if (!(findBy instanceof IByMobile)) {
            return super.findElement(findBy);
        }

        if (switchContext) {
            switchToNativeAppContext();
        }

        if (findBy instanceof IByMobileXPath) {
            log.trace(String.format("WebDriver.findElement(by.xpath(%1$s));", findBy));
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.xpath(findBy.toString())));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            } finally {
                if (switchContext){
                    switchToWebViewContext();
                }
            }
        }

        if (findBy instanceof ByMobileId) {
            log.trace(String.format("WebDriver.findElement(by.id(%1$s));", findBy));
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.id(findBy.toString())));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
            } finally {
                if (switchContext){
                    switchToWebViewContext();
                }
            }
        }

        log.trace(String.format("WebDriver.findElement(by.accessbilityId(%1$s));", findBy));
        try {
            return new SeleniumElement(((MobileDriver) webDriver).findElementByAccessibilityId(findBy.toString()));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new aeon.core.common.exceptions.NoSuchElementException(e, findBy);
        } finally {
            if (switchContext){
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
        if (element.getSelector() instanceof IByMobile) {
            switchToNativeAppContext();
            click(element, false);
            switchToWebViewContext();

            return;
        }

        super.click(element);
    }

    @Override
    public void set(WebControl control, WebSelectOption option, String setValue) {
        if (control.getSelector() instanceof IByMobile) {
            switchToNativeAppContext();
            sendKeysToElement(control, setValue);
            switchToWebViewContext();

            return;
        }

        super.set(control, option, setValue);
    }

    private void switchToNativeAppContext() {
        log.trace("Switching to native app context " + "NATIVE_APP");
        getMobileWebDriver().context("NATIVE_APP");
    }

    private void switchToWebViewContext() {
        log.trace("Switching to web view context " + context);
        getMobileWebDriver().context(context);
    }

    private void tapOnControl(WebControl control) {

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

            if (browserType == BrowserType.AndroidHybridApp && mobileDeviceResolutions.containsKey(windowWidth)) {
                windowHeight = mobileDeviceResolutions.get(windowWidth);
            }
        } catch (WebDriverException e) {
            log.trace(e.getMessage());
            windowWidth = (int) webRootWidth;
            windowHeight = 1920; //(int) webRootHeight;
        }

        log.trace("windowSize: " + windowWidth + "," + windowHeight);

        double xRatio = windowWidth * 1.0 / webRootWidth;
        double yRatio = windowHeight * 1.0 / webRootHeight;
        int pointX = webElementLocation.getX() + elementSize.getWidth() / 2;
        int pointY = webElementLocation.getY() + elementSize.getHeight() / 2;
        Point tapPoint = new Point((int) (pointX * xRatio), (int) (pointY * yRatio));

        log.trace("tapPoint: " + tapPoint.getX() + "," + tapPoint.getY());
        switchToNativeAppContext();
        TouchAction a = new TouchAction((AppiumDriver) getWebDriver());
        //a.tap(underlyingWebElement,elementSize.width/2, elementSize.height/2).perform();
        a.tap(tapPoint.getX(), tapPoint.getY()).perform();
        switchToWebViewContext();
    }
}
