package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.common.CompareType;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.exceptions.*;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementNotVisibleException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchCookieException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchWindowException;
import com.ultimatesoftware.aeon.core.common.helpers.OsCheck;
import com.ultimatesoftware.aeon.core.common.helpers.SendKeysHelper;
import com.ultimatesoftware.aeon.core.common.helpers.Sleep;
import com.ultimatesoftware.aeon.core.common.helpers.StringUtils;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.web.*;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByXPath;
import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import com.ultimatesoftware.aeon.core.extensions.IUploaderExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IWebAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.AeonTestExecution;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.IJavaScriptFlowExecutor;
import com.ultimatesoftware.aeon.extensions.selenium.jquery.SeleniumScriptExecutor;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.ultimatesoftware.aeon.core.common.helpers.DateTimeExtensions.approximatelyEquals;
import static com.ultimatesoftware.aeon.core.common.helpers.StringUtils.like;
import static com.ultimatesoftware.aeon.core.common.helpers.StringUtils.normalizeSpacing;

/**
 * Web adapter for Selenium.
 */
public class SeleniumAdapter implements IWebAdapter, AutoCloseable {

    private static final String RESULT = "Result: {}";
    private static final String VALUE = "value";
    private static final String SELECT = "select";
    private static final String WEBDRIVER_SWITCHTO_WINDOW = "WebDriver.SwitchTo().Window({});";
    private static final String WEBDRIVER_SWITCHTO_ALERT = "WebDriver.SwitchTo().Alert();";
    private static final String OPTION_NORMALIZE_SPACE = ".//option[normalize-space(.) = ";
    private static final String OPTION_VALUE = "option[value='";
    private static final String OPTGROUP_LABEL = "optgroup[label='";
    private static final String INNERHTML = "INNERHTML";
    private final URL seleniumHubUrl;
    protected WebDriver webDriver;
    private IJavaScriptFlowExecutor javaScriptExecutor;
    private IJavaScriptFlowExecutor asyncJavaScriptExecutor;
    protected IBrowserType browserType;
    private static Logger log = LoggerFactory.getLogger(SeleniumAdapter.class);
    private boolean isRemote;
    private String seleniumLogsDirectory;
    private LoggingPreferences loggingPreferences;
    private BrowserSize fallbackBrowserSize;
    private ActionsFactory actionsFactory;
    private FileDownloadHelper fileDownloadHelper;

    /**
     * Constructor for Selenium Adapter.
     *
     * @param seleniumWebDriver       The driver for the adapter.
     * @param javaScriptExecutor      The javaScript executor for the adapter.
     * @param asyncJavaScriptExecutor The asynchronous javaScript executor for the adapter.
     * @param configuration           The configuration object.
     * @param fallbackBrowserSize     The size the browser will be maximized to.
     * @param seleniumHubUrl          The used Selenium hub URL.
     * @param loggingPreferences      Preferences which contain which Selenium log types to enable
     */
    public SeleniumAdapter(
            WebDriver seleniumWebDriver,
            IJavaScriptFlowExecutor javaScriptExecutor,
            IJavaScriptFlowExecutor asyncJavaScriptExecutor,
            SeleniumConfiguration configuration,
            BrowserSize fallbackBrowserSize,
            URL seleniumHubUrl,
            LoggingPreferences loggingPreferences
    ) {
        this.javaScriptExecutor = javaScriptExecutor;
        this.asyncJavaScriptExecutor = asyncJavaScriptExecutor;
        this.webDriver = seleniumWebDriver;
        this.browserType = configuration.getBrowserType();
        this.isRemote = seleniumHubUrl != null;
        this.seleniumHubUrl = seleniumHubUrl;
        this.seleniumLogsDirectory = configuration.getString(SeleniumConfiguration.Keys.LOGGING_DIRECTORY, "log");
        this.loggingPreferences = loggingPreferences;
        this.fallbackBrowserSize = fallbackBrowserSize;
        this.actionsFactory = new ActionsFactory();
        this.fileDownloadHelper = new FileDownloadHelper();
    }

    /**
     * Gets the web driver.
     *
     * @return The web driver is returned.
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public final void addCookie(IWebCookie cookie) {
        log.trace("WebDriver.add_Cookie();");

        // Needed for bug in gecko driver. https://bugzilla.mozilla.org/show_bug.cgi?id=1415828
        String domain = cookie.getDomain();
        if (browserType == BrowserType.FIREFOX && cookie.getDomain().charAt(0) == '.') {
            domain = domain.substring(1);
        }

        Cookie c = new Cookie(cookie.getName(), cookie.getValue(), domain, cookie.getPath(), cookie.getExpiration(), cookie.getSecure());
        getWebDriver().manage().addCookie(c);
    }

    /**
     * Deletes all cookies.
     */
    public final void deleteAllCookies() {
        log.trace("WebDriver.delete_AllCookies();");
        getWebDriver().manage().deleteAllCookies();
    }

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be deleted.
     */
    public final void deleteCookie(String name) {
        log.trace("WebDriver.delete_Cookie();");
        getWebDriver().manage().deleteCookieNamed(name);
    }

    /**
     * Gets the list of all cookies.
     *
     * @return The list of cookies.
     */
    public final List<IWebCookie> getAllCookies() {
        log.trace("WebDriver.get_AllCookies();");

        List<IWebCookie> result = getWebDriver().manage().getCookies()
                .stream()
                .map(SeleniumCookie::new)
                .collect(Collectors.toList());

        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    public final IWebCookie getCookie(String name) {
        log.trace("WebDriver.get_Cookie();");
        Cookie cookie = getWebDriver().manage().getCookieNamed(name);
        if (cookie == null) {
            throw new NoSuchCookieException(name);
        }

        IWebCookie result = new SeleniumCookie(cookie);
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public final void modifyCookie(String name, String value) {
        log.trace("WebDriver.modify_Cookie();");
        Cookie cookie = getWebDriver().manage().getCookieNamed(name);

        // check if cookie actually exists.
        if (cookie == null) {
            throw new NoSuchCookieException(name);
        }

        // Delete old cookie, then add a new one with the new value.
        getWebDriver().manage().deleteCookieNamed(name);

        // Needed for bug in gecko driver. https://bugzilla.mozilla.org/show_bug.cgi?id=1415828
        String domain = cookie.getDomain();
        if (browserType == BrowserType.FIREFOX && cookie.getDomain().charAt(0) == '.') {
            domain = domain.substring(1);
        }

        Cookie newCookie = new Cookie(cookie.getName(), value, domain, cookie.getPath(), cookie.getExpiry());
        getWebDriver().manage().addCookie(newCookie);
    }

    /**
     * Gets the title of the current window.
     *
     * @return A string representation of the window's title.
     */
    public final String getTitle() {
        log.trace("WebDriver.get_Title();");
        String result = webDriver.getTitle();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets the URL of the current browser window.
     *
     * @return A URI object of the URL of the browser's window.
     */
    public final URL getUrl() {
        log.trace("WebDriver.get_Url();");
        String result = webDriver.getCurrentUrl();
        log.trace(RESULT, result);
        try {
            return new URL(result);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Gets the currently focused element.
     *
     * @return The currently focused element.
     */
    public final WebControl getFocusedElement() {
        log.trace("WebDriver.switch_To().active_Element()");
        org.openqa.selenium.WebElement result = webDriver.switchTo().activeElement();
        log.trace(RESULT, result);
        return new SeleniumElement(result);
    }

    @Override
    public String getElementTagName(WebControl element) {
        return ((SeleniumElement) element).getUnderlyingWebElement().getTagName();
    }


    @Override
    public void clearElement(WebControl element) {
        ((SeleniumElement) element).clear();
    }

    @Override
    public void chooseSelectElementByValue(WebControl element, String value) {
        ((SeleniumElement) element).selectByValue(value);
    }

    @Override
    public void chooseSelectElementByText(WebControl element, String value) {
        ((SeleniumElement) element).selectByText(value);
    }


    @Override
    public void clickElement(WebControl element) {
        ((SeleniumElement) element).getUnderlyingWebElement().click();
    }

    @Override
    public void sendKeysToElement(WebControl element, String s) {
        ((SeleniumElement) element).getUnderlyingWebElement().sendKeys(s);
    }

    @Override
    public String getElementAttribute(WebControl element, String s) {
        return ((SeleniumElement) element).getUnderlyingWebElement().getAttribute(s);
    }

    @Override
    public void switchToMainWindow(String mainWindowHandle, Boolean waitForAllPopupWindowsToClose) {
        webDriver.switchTo().window(mainWindowHandle);
        if (waitForAllPopupWindowsToClose && getWindowHandles().size() > 1) {
            throw new NotAllPopupWindowsClosedException();
        }
    }

    /**
     * Gets the current browser window's handle.
     *
     * @return A string representation of the current browser window's handle.
     */
    public final String getCurrentWindowHandle() {
        log.trace("WebDriver.get_CurrentWindowHandle();");
        String result = webDriver.getWindowHandle();
        log.trace(RESULT, result);
        return result;
    }

    /**
     * Gets all the handles of the current browser window.
     *
     * @return A collection of all handles in the current browser window.
     */
    public final Collection<String> getWindowHandles() {
        log.trace("WebDriver.get_WindowHandles();");
        return webDriver.getWindowHandles();
    }

    /**
     * Navigates to the URL passed.
     *
     * @param url The URL to navigate to.
     * @return A string representation of the handle in which is navigating to the URL.
     */
    public final String goToUrl(URL url) {
        log.trace("WebDriver.Navigate().goToUrl(\"{}\");", url);

        webDriver.navigate().to(url);

        return getCurrentWindowHandle();
    }

    /**
     * Scrolls to the top of the page.
     */
    public final void scrollToTop() {
        executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scrolls to the end of the page.
     */
    public final void scrollToEnd() {
        executeScript("window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);");
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    public WebControl findElement(IBy findBy) {

        if (findBy instanceof IByXPath) {
            log.trace("WebDriver.findElement(by.xpath({}));", findBy);
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.xpath(findBy.toString())));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementException(e, (IByXPath) findBy);
            }
        }

        if (findBy instanceof com.ultimatesoftware.aeon.core.common.web.selectors.By) {
            log.trace("WebDriver.findElement(by.cssSelector({}));", findBy);
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.cssSelector(findBy.toString())));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementException(e, findBy);
            }
        }

        if (findBy instanceof ByJQuery) {
            return (WebControl) findElements(findBy).toArray()[0];
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Finds all elements with the matching IBy.
     *
     * @param findBy Selector passed to search.
     * @return A ReadOnlyCollection of IWebElementAdapter.
     */
    public final Collection<WebControl> findElements(IBy findBy) {
        com.ultimatesoftware.aeon.core.common.web.selectors.By by =
                (com.ultimatesoftware.aeon.core.common.web.selectors.By)
                        ((findBy instanceof com.ultimatesoftware.aeon.core.common.web.selectors.By) ? findBy : null);

        if (by != null) {
            Collection<WebControl> collection;
            log.trace("WebDriver.findElements(by.cssSelector({}));", by);

            try {
                collection = webDriver.findElements(org.openqa.selenium.By.cssSelector(findBy.toString()))
                        .stream()
                        .map(SeleniumElement::new)
                        .collect(Collectors.toList());
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementsException(e, by);
            }

            if (collection.isEmpty()) {
                throw new NoSuchElementsException(by);
            }

            return collection;
        }

        ByJQuery byJQuery = (ByJQuery) ((findBy instanceof ByJQuery) ? findBy : null);
        if (byJQuery != null) {
            return findElements(byJQuery);
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of all elements matching the jQuery selector.
     *
     * @param findBy The element(s) to find.
     * @return A ReadOnlyCollection of IWebElementAdapters.
     */
    private Collection<WebControl> findElements(ByJQuery findBy) {
        String script = findBy.toString(JQueryStringType.RETURN_ELEMENT_ARRAY);
        Object result = executeScript(script);

        if (result instanceof Collection<?> && ((Collection<?>) result).isEmpty()) {
            throw new NoSuchElementsException(findBy);
        }

        try {
            Collection<org.openqa.selenium.WebElement> elements =
                    (Collection<org.openqa.selenium.WebElement>) result;

            return elements.stream().map(SeleniumElement::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ScriptReturnValueNotHtmlElementException(result, script, e);
        }
    }

    /**
     * Finds an element within HTML code with the {@code <select>} tag.
     *
     * @param findBy Selector object that we are using the find the {@code <select>} tag.
     * @return IWebSelectElementAdapter matching the findBy.
     */
    public final WebControl findSelectElement(IByWeb findBy) {
        WebControl webElement = findElement(findBy);

        SeleniumElement seleniumElement = (SeleniumElement) webElement;

        return new SeleniumSelectElement(new Select(seleniumElement.getUnderlyingWebElement()));
    }

    /**
     * Switches to the default content of the current browser window.
     */
    public final void switchToDefaultContent() {
        log.trace("WebDriver.SwitchTo().DefaultContent();");
        webDriver.switchTo().defaultContent();
    }

    /**
     * Switches to the specified frame in the HTML {@code <frame>}.
     *
     * @param findBy The selector of the element {@code <frame>} to switch to.
     */
    public final void switchToFrame(IByWeb findBy) {
        WebControl webElement = findElement(findBy);

        SeleniumElement seleniumElement = (SeleniumElement) webElement;

        log.trace("WebDriver.SwitchTo().Frame(<IWebElement>);");

        webDriver.switchTo().frame(seleniumElement.getUnderlyingWebElement());
    }

    /**
     * Switches to the window with the corresponding windowTitle.
     *
     * @param windowTitle The title of the window we are searching for.
     * @return A string representation of the windowTitle we are searching for.
     */
    public final String switchToWindowByTitle(String windowTitle) {
        if (StringUtils.isBlank(windowTitle)) {
            throw new IllegalArgumentException("windowTitle is null or an empty string");
        }
        for (String window : getWindowHandles()) {
            log.trace(WEBDRIVER_SWITCHTO_WINDOW, window);

            webDriver.switchTo().window(window);

            if (getTitle().toLowerCase().contains(windowTitle.toLowerCase())) {
                return window;
            }
        }

        throw new NoSuchWindowException(windowTitle);
    }

    /**
     * Switches to the window with the corresponding 'handle' passed.
     *
     * @param handle The handle to switch to.
     */
    public final void switchToWindowByHandle(String handle) {
        log.trace(WEBDRIVER_SWITCHTO_WINDOW, handle);
        webDriver.switchTo().window(handle);
    }

    /**
     * Switches to the window with the corresponding URL value.
     *
     * @param value The URL being searched for.
     * @return A string representation of the window's handle we switched to.
     */
    public final String switchToWindowByUrl(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(VALUE);
        }

        for (String window : getWindowHandles()) {
            log.trace(WEBDRIVER_SWITCHTO_WINDOW, window);

            webDriver.switchTo().window(window);

            URL url = getUrl();
            if (url != null && url.toString().toLowerCase().contains(value.toLowerCase())) {
                return window;
            }
        }

        throw new NoSuchWindowException(value);
    }

    /**
     * Closes the current instance of the web browser, but does not quit() the driver.
     */
    public final void close() {
        switchToDefaultContent();
        log.trace("WebDriver.close();");
        webDriver.close();
    }

    /**
     * close()'s and terminates 'this' instance of the browser and the WebDriver.
     */
    public void quit() {

        log.trace("WebDriver.quit();");

        if (isRemote && (
                browserType == BrowserType.CHROME
                        || browserType == BrowserType.EDGE
                        || browserType == BrowserType.FIREFOX
                        || browserType == BrowserType.OPERA
                        || browserType == BrowserType.SAFARI
                        || browserType == BrowserType.INTERNET_EXPLORER
        )) {

            SessionId sessionId = ((RemoteWebDriver) webDriver).getSessionId();

            if (sessionId == null) {
                throw new IllegalStateException("session ID is null before calling \"quit\". Please inspect the trace logs for possible earlier errors.");
            }

            collectSeleniumLogs();

            webDriver.quit();

            String videoPath = fileDownloadHelper.downloadVideo(seleniumHubUrl, sessionId.toString());

            if (videoPath != null) {
                AeonTestExecution.executionEvent("videoDownloaded", videoPath);

                List<IUploaderExtension> extensions = Aeon.getExtensions(IUploaderExtension.class);
                for (IUploaderExtension extension : extensions) {
                    String videoUrl = extension.onUploadRequested(videoPath, "video", "Video URL");

                    if (videoUrl != null) {
                        log.info("Video uploaded: {}", videoUrl);
                    }
                }
            }

            return;
        }

        collectSeleniumLogs();

        webDriver.quit();
    }

    /**
     * Verifies there is an alert raised on the page.
     */
    public final void verifyAlertExists() {
        log.trace(WEBDRIVER_SWITCHTO_ALERT);

        try {
            webDriver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Verifies there is NOT an alert raised on the page.
     */
    public final void verifyAlertNotExists() {
        log.trace(WEBDRIVER_SWITCHTO_ALERT);

        try {
            webDriver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            return;
        }

        throw new AlertExistsException();
    }

    /**
     * Get the body text of the alert that is raised on the page.
     *
     * @return The description of the alert dialog box.
     */
    public final String getAlertText() {
        try {
            log.trace("WebDriver.SwitchTo().Alert().get_Text();");
            return webDriver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Sends the keysToSend to the alert in the current browser window.
     *
     * @param keysToSend Keys to send to the alert.
     */
    public final void sendKeysToAlert(String keysToSend) {
        try {
            log.trace("WebDriver.SwitchTo().Alert().sendKeys({});", keysToSend);
            Alert alert = webDriver.switchTo().alert();
            alert.sendKeys(keysToSend);
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Accepts an alert on the page.
     */
    public void acceptAlert() {
        try {
            log.trace(WEBDRIVER_SWITCHTO_ALERT);
            webDriver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Dismisses an alert on the page.
     */
    public void dismissAlert() {
        try {
            log.trace("WebDriver.SwitchTo().Alert().Dismiss();");
            webDriver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Focuses the current browser window.
     */
    public void focusWindow() {
        // Only implemented for InternetExplorer
    }

    /**
     * Executes the script(JavaScript) passed.
     *
     * @param script Script to execute.
     * @param args   Args to pass to JavaScriptExecutor.
     * @return An object returned from the script executed.
     */
    public final Object executeScript(String script, Object... args) {
        try {
            return javaScriptExecutor.getExecutor()
                    .apply(new SeleniumScriptExecutor(webDriver), script, Arrays.asList(args));
        } catch (RuntimeException e) {
            throw new ScriptExecutionException(script, e);
        }
    }

    /**
     * Executes asynchronous JavaScript.
     *
     * @param script Script to execute.
     * @param args   Args to pass to JavaScriptExecutor.
     * @return An object returned from the script executed.
     */
    public final Object executeAsyncScript(String script, Object... args) {
        try {
            return asyncJavaScriptExecutor.getExecutor()
                    .apply(new SeleniumScriptExecutor(webDriver), script, Arrays.asList(args));
        } catch (RuntimeException e) {
            throw new ScriptExecutionException(script, e);
        }
    }

    /**
     * Accesses the history of the browser to execute the back function.
     */
    public final void back() {
        executeScript("window.history.back();");
    }

    /**
     * Accesses the history of the browser to execute the forward function.
     */
    public final void forward() {
        executeScript("window.history.forward();");
    }

    /**
     * Refreshes the current window in the active browser.
     */
    public final void refresh() {
        log.trace("WebDriver.Navigate().refresh();");
        webDriver.navigate().refresh();
    }

    /**
     * Blurs the current element.
     * A globally unique identifier associated with this call.
     *
     * @param element The element to be blurred.
     */
    public final void blur(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.BlurElement));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.BLUR_ELEMENT));
    }

    /**
     * Maximizes the browser window.
     * <p>
     * Workaround implemented for Chromium browsers running in MacOS due to maximize default behaviour
     * only expanding vertically. More information can be found at:
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=985
     * Also problems with maximize in Chrome 60:
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=1901
     */
    public void maximize() {
        try {
            log.trace("Webdriver.Manage().Window.maximize();");
            if (isRemote && (browserType.equals(BrowserType.CHROME) ||
                    browserType.equals(BrowserType.FIREFOX) ||
                    browserType.equals(BrowserType.OPERA))) {
                java.awt.Dimension dimension = BrowserSizeMap.map(fallbackBrowserSize);
                log.trace("Setting manual size  for remote test on chrome, firefox, or opera.");
                webDriver.manage().window().setPosition(new Point(0, 0));
                webDriver.manage().window().setSize(new Dimension(dimension.width, dimension.height));
            } else if (!isRemote && osIsMacOrLinux() && (browserType.equals(BrowserType.OPERA) ||
                    browserType.equals(BrowserType.CHROME))) {
                int screenWidth = ScreenHelper.getScreenWidth();
                int screenHeight = ScreenHelper.getScreenHeight();
                Point position = new Point(0, 0);
                webDriver.manage().window().setPosition(position);
                Dimension maximizedScreenSize =
                        new Dimension(screenWidth, screenHeight);
                log.trace("Using maximize workaround on local Mac or Linux machines with resolution {}", maximizedScreenSize);
                webDriver.manage().window().setSize(maximizedScreenSize);
            } else {
                webDriver.manage().window().maximize();
            }
        } catch (IllegalStateException e) {
            log.trace("window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
            executeScript("window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
        }
    }

    /**
     * Resizes the window to the desired Size dimensions.
     *
     * @param size Desired dimensions to resize the window to.
     */
    public void resize(java.awt.Dimension size) {
        log.trace("WebDriver.Manage().Window.set_Size({});", size);
        webDriver.manage().window().setSize(new Dimension(size.width, size.height));
    }

    @Override
    public void scrollElementIntoView(WebControl element) {
        scrollElementIntoView(element.getSelector());
    }

    /**
     * Scrolls the element specified by the provided 'selector' into view.
     *
     * @param selector Element to scroll into view.
     */
    protected void scrollElementIntoView(IByWeb selector) {
        executeScript(selector.toJQuery().toString(JQueryStringType.SCROLL_ELEMENT_INTO_VIEW));
        executeScript("$(\"body\").scrollLeft(0);");
    }

    /**
     * Returns a screenshot of the current browser window.
     *
     * @return An Image object of the current browser.
     */
    public java.awt.Image getScreenshot() {
        TakesScreenshot driver = (TakesScreenshot) ((webDriver instanceof TakesScreenshot) ? webDriver : null);

        if (driver == null) {
            throw new IllegalStateException("Web IDriver does not support taking screenshot");
        }

        log.trace("webDriver.getScreenshotAs(bytes);");

        byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            throw new UnableToTakeScreenshotException();
        }
    }

    /**
     * Gets the source code of the page (HTML).
     *
     * @return A string representation of the source code of the page.
     */
    public final String getPageSource() {
        log.trace("WebDriver.get_PageSource();");

        return webDriver.getPageSource();
    }

    /**
     * Drag and drops the draggableElement to the targetElement area.
     *
     * @param draggableElement Element to be dragged.
     * @param targetElement    Where the element will be dropped.
     */
    public final void dragAndDrop(WebControl draggableElement, IByWeb targetElement) {
        WebElement draggable = ((SeleniumElement) draggableElement).getUnderlyingWebElement();
        WebElement target = ((SeleniumElement) findElement(targetElement)).getUnderlyingWebElement();

        if (isHtml5Draggable(draggable)) {
            log.trace("Executing drag and drop with javascript script");
            executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
                    + "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
                    + "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
                    + "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
                    + "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
                    + "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
                    + "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
                    + "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
                    + "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
                    + "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
                    + "var dropEvent = createEvent('drop');\n"
                    + "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
                    + "var dragEndEvent = createEvent('dragend');\n"
                    + "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
                    + "var source = arguments[0][0];\n" + "var destination = arguments[0][1];\n"
                    + "simulateHTML5DragAndDrop(source, destination)", draggable, target);
        } else {
            log.trace("new Actions(IWebDriver).dragAndDrop(IWebElement, IWebElement);");
            Actions builder = actionsFactory.createActions(webDriver);
            builder.clickAndHold(draggable).perform();
            Sleep.getInstance().wait(250);

            if (browserType == BrowserType.FIREFOX) {
                scrollElementIntoView(targetElement);
            }

            builder.moveToElement(target).perform();
            builder.release(target).perform();
        }
    }

    private boolean isHtml5Draggable(WebElement draggableElement) {
        String draggable = draggableElement.getAttribute("draggable");
        if (draggable == null) {
            return false;
        }
        return draggable.equalsIgnoreCase("true");
    }

    /**
     * Performs a rightClick on the element passed as an argument.
     *
     * @param element The element to perform the rightClick on.
     */
    public final void rightClick(WebControl element) {
        log.trace("new Actions(IWebDriver).ContextClick(IWebElement);");

        if (browserType == BrowserType.FIREFOX || browserType == BrowserType.INTERNET_EXPLORER) {
            scrollElementIntoView(element);
        }
        (actionsFactory.createActions(webDriver)).contextClick(
                ((SeleniumElement) element).getUnderlyingWebElement())
                .perform();
    }

    /**
     * Performs a doubleClick on the element passed as an argument.
     *
     * @param element The element to perform the doubleClick on.
     */
    public final void doubleClick(WebControl element) {
        if (this.browserType == BrowserType.FIREFOX) {
            doubleClickByJavaScript(element);
            return;
        }

        if (this.browserType == BrowserType.INTERNET_EXPLORER) {
            scrollElementIntoView(element);
        }
        log.trace("new Actions(IWebDriver).doubleClick(IWebElement);");

        (actionsFactory.createActions(webDriver)).doubleClick(
                ((SeleniumElement) element).getUnderlyingWebElement())
                .perform();
    }

    // work around for marionette driver v.11.1

    /**
     * Performs a doubleClick on the element passed as an argument by executing javascript.
     *
     * @param element The element to perform the doubleClick on.
     */
    private void doubleClickByJavaScript(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.FireDoubleClick));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.FIRE_DOUBLE_CLICK));
    }

    /**
     * Performs a LeftClick on the element passed as an argument.
     *
     * @param element The element to perform the click on.
     */
    public void click(WebControl element) {
        try {
            ((SeleniumElement) element).click();
        } catch (org.openqa.selenium.ElementNotVisibleException e) {
            throw new ElementNotVisibleException(element.getSelector());
        }
    }

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param element The web element to click.
     * @param x       The x offset.
     * @param y       The y offset.
     */
    public final void clickAtOffset(WebControl element, int x, int y) {
        SeleniumElement seleniumElement = (SeleniumElement) element;

        (actionsFactory.createActions(webDriver))
                .moveToElement(seleniumElement.getUnderlyingWebElement(), x, y)
                .click()
                .perform();
    }

    /**
     * Refreshes the current frame.
     */
    public final void refreshFrame() {
        executeScript("window.location = location.href;");
    }

    /**
     * Clicks and holds for the duration specified.
     *
     * @param element  The web element to click.
     * @param duration click for at least this long (in milliseconds).
     *                 <p>
     *                 Due to the way Windows handles event messages, thread context switches, thread sleep/awake, and Windows time ticks,
     *                 this command can only guarantee the click will be held for AT LEAST the time specified, not that the
     *                 click will be held for exactly the duration specified. It should have an accuracy approaching 15ms, but
     *                 depending on the system, it could be much worse. In some cases, the accuracy is much better, approaching
     *                 1ms. This can happen on Win7 and .Net 4 and higher, but having those do not guarantee it.
     *                 </p>
     *                 <p>
     *                 Another consideration to take into account is the specific browser you are using this command with.
     *                 Initial testing showed IE and Firefox to have much worse response time with this command in comparison to
     *                 Chrome.
     *                 </p>
     *                 <p>
     *                 If, for whatever reason, the command is interrupted and the click is not released, behavior can be quite weird.
     *                 The correct way to programatically recover from such a case would be to call Release() on the element that clickAndHold
     *                 was called on, but any call that triggers a MouseUp event should suffice.
     *                 </p>
     */
    public void clickAndHold(WebControl element, int duration) {
        SeleniumElement seleniumElement = (SeleniumElement) element;
        Actions action = actionsFactory.createActions(webDriver);

        // click.
        if (browserType == BrowserType.FIREFOX || browserType == BrowserType.INTERNET_EXPLORER) {
            scrollElementIntoView(seleniumElement);
        }

        action.clickAndHold(seleniumElement.getUnderlyingWebElement()).perform();

        // Hold the click.
        Sleep.getInstance().wait(duration);

        // Release click.
        action.release(seleniumElement.getUnderlyingWebElement()).perform();
    }

    /**
     * Checks a checkbox.
     *
     * @param element The checkbox to be checked.
     */
    public void checkElement(WebControl element) {
        if (!((SeleniumElement) element).getUnderlyingWebElement().isSelected()) {
            click(element);
        }
    }

    /**
     * Unchecks a checkbox.
     *
     * @param element The checkbox to be unchecked.
     */
    public void unCheckElement(WebControl element) {
        if (((SeleniumElement) element).getUnderlyingWebElement().isSelected()) {
            click(element);
        }
    }

    /**
     * Checks that an element is disabled.
     *
     * @param element The web element to check.
     */
    @Override
    public void isElementDisabled(WebControl element) {
        if (((SeleniumElement) element).enabled()) {
            throw new ElementIsEnabledException(element.getSelector());
        }
    }

    /**
     * Checks that an element is enabled.
     *
     * @param element The web element to check.
     */
    public void isElementEnabled(WebControl element) {
        if (!((SeleniumElement) element).enabled()) {
            throw new ElementNotEnabledException(element.getSelector());
        }
    }

    /**
     * If this method was called then the element exists.
     *
     * @param element The web element.
     */
    public void exists(WebControl element) {
        //Logic done at command initialization
    }

    /**
     * If this method was reached then the element exists when it should not.
     *
     * @param element The web element.
     */
    public void notExists(WebControl element) {
        //IF execution reached here then element exists.
        throw new ElementExistsException(element.getSelector());
    }

    private void hasOptions(SeleniumElement element, String[] options, WebSelectOption select) {
        try {
            for (String desiredOption : options) {
                findOption(element, select, desiredOption);
            }
        } catch (NoSuchElementException e) {
            throw new ElementDoesNotHaveOptionException(e.toString());
        }
    }

    private void doesNotHaveOptions(SeleniumElement element, String[] options, WebSelectOption select) {
        for (String desiredOption : options) {
            boolean elementFound = true;
            try {
                findOption(element, select, desiredOption);
            } catch (NoSuchElementException e) {
                elementFound = false;
            }
            if (elementFound) {
                throw new ElementHasOptionException(desiredOption);
            }
        }
    }

    private void findOption(SeleniumElement element, WebSelectOption select, String desiredOption) {
        if (select == WebSelectOption.TEXT) {
            element.findElementByXPath(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTION_NORMALIZE_SPACE + Quotes.escape(desiredOption) + "]"));
        } else {
            element.findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTION_VALUE.concat(desiredOption).concat("']")));
        }
    }

    @Override
    public void elementHasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT)) {
            throw new IncorrectElementTagException(SELECT, ((SeleniumElement) element).getTagName());
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTGROUP_LABEL.concat(optgroup).concat("']")));
            hasOptions(group, options, select);
        } else {
            hasOptions((SeleniumElement) element, options, select);
        }
    }

    @Override
    public void elementDoesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT)) {
            throw new IncorrectElementTagException(SELECT, ((SeleniumElement) element).getTagName());
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTGROUP_LABEL.concat(optgroup).concat("']")));
            doesNotHaveOptions(group, options, select);
        } else {
            doesNotHaveOptions((SeleniumElement) element, options, select);
        }
    }

    /**
     * Takes the mouse pointer off an element.
     *
     * @param element The element to take the mouse pointer off of.
     */
    @Override
    public void mouseOut(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.mouseOut));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.MOUSE_OUT));
    }

    /**
     * Moves the mouse pointer over an element.
     *
     * @param element The element to move the mouse pointer over.
     */
    @Override
    public void mouseOver(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.mouseOver));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.MOUSE_OVER));
    }

    /**
     * Method asserts that the selected element's body tag will be changed into the provided String value.
     *
     * @param element by The selector.
     * @param value   Html to be inserted into body tag
     */
    @Override
    public void setBodyValueByJavaScript(WebControl element, String value) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.SetBodyText));");
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SET_BODY_TEXT), Quotes.escape(value)));
    }

    /**
     * Method asserts that the selected element's value tag will be changed into the provided String value.
     *
     * @param element by The selector.
     * @param value   Html to be inserted into a value tag
     */
    @Override
    public void setTextByJavaScript(WebControl element, String value) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.SetValueText));");
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SET_ELEMENT_TEXT), Quotes.escape(value)));
    }

    /**
     * Method asserts that the selected element's div tag will be changed into the provided String value.
     *
     * @param element by The selector.
     * @param value   Html to be inserted into div tag
     */
    @Override
    public void setDivValueByJavaScript(WebControl element, String value) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.SetDivText));");
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SET_DIV_TEXT), Quotes.escape(value)));
    }

    /**
     * Method that clicks all elements that correspond with the given IBy.
     *
     * @param elementsBy The IBy that corresponds with all the elements to click.
     */
    public void clickAllElements(IByWeb elementsBy) {
        Collection<WebControl> elements = findElements(elementsBy);
        for (WebControl element : elements) {
            clickElement(element);
        }
    }

    /**
     * Asserts that a select has all the specified elements in that order. Can optionally specify which option group the elements are a part of.
     *
     * @param element  The select element to search.
     * @param options  The options the element should have in the same order.
     * @param optgroup The option group the options should be a part of.
     * @param select   The method by which the options are identified, either their value, or their visible text.
     */
    public void elementHasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT)) {
            throw new IncorrectElementTagException(SELECT, ((SeleniumElement) element).getTagName());
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTGROUP_LABEL + optgroup + "']"));
            elementHasOptionsInOrder(group, options, select);
        } else {
            elementHasOptionsInOrder((SeleniumElement) element, options, select);
        }
    }

    private void elementHasOptionsInOrder(SeleniumElement element, String[] options, WebSelectOption select) {
        try {
            if (options.length > 1) {
                if (select == WebSelectOption.TEXT) {
                    element = (SeleniumElement) element.findElementByXPath(
                            com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTION_NORMALIZE_SPACE + Quotes.escape(options[0]) + "]"));
                }

                for (int i = 1; i < options.length; i++) {
                    if (select == WebSelectOption.VALUE) {
                        element.findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTION_VALUE + options[i - 1] + "'] ~ option[value='" + options[i] + "']"));
                    } else {
                        element = (SeleniumElement) element.findElementByXPath(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[i]) + "]"));
                    }
                }
            } else {
                if (select == WebSelectOption.VALUE) {
                    element.findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTION_VALUE + options[0] + "']"));
                } else {
                    element.findElementByXPath(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[0]) + "]"));
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException | NoSuchElementException e) {
            throw new ElementDoesNotHaveOptionException(e.toString());
        }
    }

    /**
     * Asserts that a select element has a certain number of options. Can optionally specify an option group instead of the entire select.
     *
     * @param element   The select element.
     * @param optnumber The amount of options the element should have.
     * @param optgroup  The optional option group to be searched.
     */
    public void hasNumberOfOptions(WebControl element, int optnumber, String optgroup) {
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT)) {
            throw new IncorrectElementTagException(SELECT, ((SeleniumElement) element).getTagName());
        }
        Collection<WebControl> options = findAllOptions(element, optgroup);
        if (options.size() != optnumber) {
            throw new ElementDoesNotHaveNumberOfOptionsException(options.size(), optnumber);
        }
    }

    /**
     * Asserts that a select has all of its options in lexicographic order. The order can either be ascending or descending alphanumeric order by either the options value or their text.
     * Can optionally be passed an option group which will be searched instead of the entire select.
     *
     * @param element  The select element to be searched.
     * @param compare  The method by which the options will be compared.
     * @param optGroup An optional option group which would be searched in isolation instead of all the options under select.
     */
    public void hasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup) {
        Collection<WebControl> elements = findAllOptions(element, optGroup);
        Iterator<WebControl> elementsIterator = elements.iterator();
        SeleniumElement prevOption = (SeleniumElement) elementsIterator.next();
        SeleniumElement currOption;
        while (elementsIterator.hasNext()) {
            currOption = (SeleniumElement) elementsIterator.next();
            ifAscendingOrDescendingByTextOrValueThenThrowException(compare, prevOption, currOption);
            prevOption = currOption;
        }
    }

    private Collection<WebControl> findAllOptions(WebControl element, String optgroup) {
        if (optgroup != null) {
            element = ((SeleniumElement) element).findElement(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(OPTGROUP_LABEL + optgroup + "']"));
        }
        return ((SeleniumElement) element).findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector("option"));
    }

    /**
     * A helper method for hasAllOptionsInOrder containing a switch to check for different cases by text or value.
     *
     * @param compare    The element helping to compare.
     * @param prevOption The previous selenium option.
     * @param currOption The current selenium option.
     */
    private void ifAscendingOrDescendingByTextOrValueThenThrowException(CompareType compare, SeleniumElement prevOption, SeleniumElement currOption) {
        switch (compare) {
            case ASCENDING_BY_TEXT:
                if (prevOption.getText().toLowerCase().compareTo(currOption.getText().toLowerCase()) > 0) {
                    throw new ElementsNotInOrderException(compare);
                }
                break;
            case DESCENDING_BY_TEXT:
                if (prevOption.getText().toLowerCase().compareTo(currOption.getText().toLowerCase()) < 0) {
                    throw new ElementsNotInOrderException(compare);
                }
                break;
            case ASCENDING_BY_VALUE:
                if (prevOption.getAttribute(VALUE).toLowerCase().compareTo(currOption.getAttribute(VALUE)) > 0) {
                    throw new ElementsNotInOrderException(compare);
                }
                break;
            case DESCENDING_BY_VALUE:
                if (prevOption.getAttribute(VALUE).toLowerCase().compareTo(currOption.getAttribute(VALUE)) < 0) {
                    throw new ElementsNotInOrderException(compare);
                }
                break;
        }
    }

    /**
     * Checks to see if a selected element's checkbox is selected.
     *
     * @param element The select element.
     */
    public void selected(WebControl element) {
        if (!((SeleniumElement) element).selected()) {
            throw new ElementNotSelectedException();
        }
    }

    /**
     * Checks to see if a selected element's checkbox is not selected.
     *
     * @param element The select element.
     */
    public void notSelected(WebControl element) {
        if (((SeleniumElement) element).selected()) {
            throw new ElementIsSelectedException();
        }
    }

    /**
     * Checks to see if a selected element is visible.
     *
     * @param element The select element.
     */
    public void visible(WebControl element) {
        if (!((SeleniumElement) element).displayed()) {
            throw new ElementNotVisibleException(element.getSelector());
        }
    }

    /**
     * Checks to see if a selected element is hidden.
     *
     * @param element The select element.
     */
    public void notVisible(WebControl element) {
        if (((SeleniumElement) element).displayed()) {
            throw new ElementIsVisibleException(element.getSelector());
        }
    }


    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        Collection<String> elements = findOptions((SeleniumElement) element, selector, option, attribute);
        if (elements != null) {
            for (String value : values) {
                if (!elements.contains(value)) {
                    throw new ElementDoesNotHaveException(value);
                }
            }
        }
    }

    /**
     * Asserts that an elements children do not possess a text.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the children should not possess.
     * @param selector  The selector for the children to be searched.
     * @param option    The comparison option to be compared.
     * @param attribute The string attribute to get.
     */
    public void doesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        Collection<String> elements = findOptions((SeleniumElement) element, selector, option, attribute);
        if (elements != null) {
            for (String value : values) {
                if (elements.contains(value)) {
                    throw new ElementHasException(value);
                }
            }
        }
    }

    @Override
    public void hasAttribute(WebControl element, String attributeName) {
        if (!((SeleniumElement) element).hasAttribute(attributeName)) {
            throw new ElementDoesNotHaveAttributeException(element, attributeName);
        }
    }

    @Override
    public void doesNotHaveAttribute(WebControl element, String attributeName) {
        if (((SeleniumElement) element).hasAttribute(attributeName)) {
            throw new ElementHasAttributeException(element, attributeName);
        }
    }

    private Collection<String> findOptions(SeleniumElement element, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        if (option == ComparisonOption.TEXT) {
            if (attribute.equalsIgnoreCase(INNERHTML)) {
                elements = element.
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText())).collect(Collectors.toList());
            } else {
                elements = element.
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute))).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.RAW) {
            elements = element.findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute))).collect(Collectors.toList());
        }

        return elements;
    }

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * Comparisons are made ignoring whitespace and case.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void hasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(x -> normalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.TEXT) {
            if (attribute.equalsIgnoreCase(INNERHTML)) {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> StringUtils.
                        normalizeSpacing(((SeleniumElement) e).getText()).toLowerCase()).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> StringUtils.
                        normalizeSpacing(((SeleniumElement) e).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.RAW) {
            elements = ((SeleniumElement) element).findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> StringUtils.
                            normalizeSpacing(((SeleniumElement) x).getAttribute(attribute).toLowerCase())).collect(Collectors.toList());
        }

        hasLikeAssertion(elements, values);
    }

    private void hasLikeAssertion(Collection<String> elements, Collection<String> values) {
        if (elements != null) {
            for (String expectedValue : values) {
                boolean foundMatch = false;
                for (String elementValue : elements) {
                    if (elementValue.contains(expectedValue)) {
                        foundMatch = true;
                        break;
                    }
                }
                if (!foundMatch) {
                    throw new ElementDoesNotHaveException(expectedValue);
                }
            }
        }
    }

    /**
     * Asserts that an elements children do not possess a text. Comparisons made ignoring case and whitespace.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the children should not possess.
     * @param selector  The selector for the children to be searched.
     * @param option    The comparison option to be compared.
     * @param attribute The string attribute to get.
     */
    public void doesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(x -> normalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.TEXT) {
            if (attribute.equalsIgnoreCase(INNERHTML)) {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText()).toLowerCase()).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.RAW) {
            elements = ((SeleniumElement) element).findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String expectedValue : values) {
                for (String elementValue : elements) {
                    if (elementValue.contains(expectedValue)) {
                        throw new ElementHasException(expectedValue);
                    }
                }
            }
        }
    }


    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void hasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        if (option == ComparisonOption.TEXT) {
            if (attribute.equalsIgnoreCase(INNERHTML)) {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText())).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute))).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.RAW) {
            elements = ((SeleniumElement) element).findElements(com.ultimatesoftware.aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute))).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String value : values) {
                if (!elements.contains(value)) {
                    throw new ElementDoesNotHaveException(value);
                }
                elements.remove(value);
            }
            if (!elements.isEmpty()) {
                throw new ElementDoesNotOnlyHaveException(elements);
            }
        }
    }

    /**
     * Asserts that an element's attribute is equal to a given value. Moreover, if the
     * attribute it either INNERHTML or VALUE, and the control is a Select element, then
     * the assertion will be evaluated against the selected option's text or value, respectively.
     *
     * @param element       The web element.
     * @param expectedValue The value the attribute should be.
     * @param option        Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute     The attribute.
     */
    public void is(WebControl element, String expectedValue, ComparisonOption option, String attribute) {
        // special check for Select elements
        // if the select element is checking value or innerhtml, check the selected option, otherwise check the element
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT) && (attribute.equalsIgnoreCase(INNERHTML) || attribute.equalsIgnoreCase(VALUE))) {
            isWithSelect(element, expectedValue, attribute);
            return;
        }
        // If Text option was selected then use getText, otherwise use getAttribute
        if (option == ComparisonOption.TEXT) {
            if (StringUtils.isNot(expectedValue, ((SeleniumElement) element).getText())) {
                throw new ValuesAreNotEqualException(((SeleniumElement) element).getText(), expectedValue);
            }
        } else {
            if (StringUtils.isNot(expectedValue, ((SeleniumElement) element).getAttribute(attribute))) {
                throw new ValuesAreNotEqualException(((SeleniumElement) element).getAttribute(attribute), expectedValue);
            }
        }
    }

    /**
     * Asserts that a Select element's attribute is equal to a given value.
     *
     * @param element       The web element.
     * @param expectedValue The value the attribute should be.
     * @param attribute     The attribute being checked.
     */
    private void isWithSelect(WebControl element, String expectedValue, String attribute) {
        if (attribute.equalsIgnoreCase(INNERHTML)) {
            String value = ((SeleniumElement) element).getSelectedOptionText();
            if (StringUtils.isNot(value, expectedValue)) {
                throw new ValuesAreNotEqualException(value, expectedValue);
            }
        } else {
            String value = getElementAttribute(((SeleniumElement) element).getSelectedOption(), attribute);
            if (StringUtils.isNot(value, expectedValue)) {
                throw new ValuesAreNotEqualException(value, expectedValue);
            }
        }
    }

    /**
     * Asserts that an element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     * Moreover, if the attribute it either INNERHTML or VALUE, and the control is a Select element, then
     * the assertion will be evaluated against the selected option's text or value, respectively.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    public void isLike(WebControl element, String value, ComparisonOption option, String attribute) {
        // special check for Select elements
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT) && (attribute.equalsIgnoreCase(INNERHTML) || attribute.equalsIgnoreCase(VALUE))) {
            isLikeWithSelect(element, value, attribute);
            return;
        }
        if (option == ComparisonOption.TEXT) {
            String actualValue = ((SeleniumElement) element).getText();
            if (!like(actualValue, value, false)) {
                throw new ValuesAreNotAlikeException(actualValue, value);
            }
        } else {
            String actualValue = ((SeleniumElement) element).getAttribute(attribute);
            if (!like(actualValue, value, false)) {
                throw new ValuesAreNotAlikeException(actualValue, value);
            }
        }
    }

    /**
     * Asserts that a Select element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element       The web element.
     * @param expectedValue The value the attribute should be.
     * @param attribute     The attribute being checked.
     */
    private void isLikeWithSelect(WebControl element, String expectedValue, String attribute) {
        if (attribute.equalsIgnoreCase(INNERHTML)) {
            String value = ((SeleniumElement) element).getSelectedOptionText();
            if (!like(value, expectedValue, false)) {
                throw new ValuesAreNotAlikeException(value, expectedValue);
            }
        } else {
            String value = getElementAttribute(((SeleniumElement) element).getSelectedOption(), attribute);
            if (!like(value, expectedValue, false)) {
                throw new ValuesAreNotAlikeException(value, expectedValue);
            }
        }
    }

    /**
     * Asserts that an element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     * Moreover, if the attribute it either INNERHTML or VALUE, and the control is a Select element, then
     * the assertion will be evaluated against the selected option's text or value, respectively.
     *
     * @param element       The web element.
     * @param expectedValue The value the attribute should be.
     * @param option        Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute     The attribute.
     */
    @Override
    public void isNotLike(WebControl element, String expectedValue, ComparisonOption option, String attribute) {
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase(SELECT) && (attribute.equalsIgnoreCase(INNERHTML) || attribute.equalsIgnoreCase(VALUE))) {
            isNotLikeWithSelect(element, expectedValue, attribute);
            return;
        }
        if (option == ComparisonOption.TEXT && attribute.equalsIgnoreCase(INNERHTML)) {
            if (like(((SeleniumElement) element).getText(), expectedValue, false)) {
                throw new ValuesAreAlikeException(expectedValue, ((SeleniumElement) element).getText());
            }
        } else {
            if (like(((SeleniumElement) element).getAttribute(attribute), expectedValue, false)) {
                throw new ValuesAreAlikeException(expectedValue, ((SeleniumElement) element).getAttribute(attribute));
            }
        }
    }

    /**
     * Asserts that a Select element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element       The web element.
     * @param expectedValue The value the attribute should be.
     * @param attribute     The attribute.
     */
    private void isNotLikeWithSelect(WebControl element, String expectedValue, String attribute) {
        try {
            isLikeWithSelect(element, expectedValue, attribute);
        } catch (ValuesAreNotAlikeException e) {
            return; // that means the values are not alike
        }
        if (attribute.equalsIgnoreCase(INNERHTML)) { // this is to make sure the correct exception is thrown
            throw new ValuesAreAlikeException(expectedValue, ((SeleniumElement) element).getSelectedOptionText());
        } else {
            throw new ValuesAreAlikeException(expectedValue, getElementAttribute(((SeleniumElement) element).getSelectedOption(), attribute));
        }
    }

    @Override
    public void verifyAlertText(String comparingText) {
        if (StringUtils.isNot(getAlertText(), comparingText)) {
            throw new ValuesAreNotEqualException(getAlertText(), comparingText);
        }
    }

    @Override
    public void verifyAlertTextLike(String comparingText, boolean caseSensitive) {
        if (!like(getAlertText(), comparingText, caseSensitive)) {
            throw new ValuesAreNotAlikeException(getAlertText(), comparingText);
        }
    }

    @Override
    public void verifyTitle(String comparingTitle) {
        if (StringUtils.isNot(getTitle(), comparingTitle)) {
            throw new ValuesAreNotEqualException(getTitle(), comparingTitle);
        }
    }

    @Override
    public void verifyURL(URL comparingURL) {

        URL url = getUrl();
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        URI uri;
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("url");
        }

        if (comparingURL == null) {
            throw new IllegalArgumentException("comparingURL");
        }

        URI comparingURI;
        try {
            comparingURI = comparingURL.toURI();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("comparingURL");
        }

        if (!uri.equals(comparingURI)) {
            throw new ValuesAreNotEqualException(url.toString(), comparingURL.toString());
        }
    }

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. Has a
     * margin of error. The date must be in the ISO-8601 standard.
     *
     * @param element       The element that possesses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expectedDate  The expected date that the attribute should possess.
     * @param delta         The margin of error that the date can be within. Cannot possess any weeks, months or years due to
     *                      them having variable lengths.
     */
    @Override
    public void datesApproximatelyEqual(WebControl element, String attributeName, LocalDate expectedDate, Period delta) {
        String actualString = ((SeleniumElement) element).getAttribute(attributeName);
        try {
            LocalDate actualDate = LocalDate.parse(actualString);
            if (!approximatelyEquals(actualDate, expectedDate, delta)) {
                throw new DatesNotApproximatelyEqualException(expectedDate, actualDate, delta);
            }
        } catch (IllegalArgumentException e) {
            throw new ElementAttributeNotADateException(attributeName, actualString);
        }
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return Returns the BrowserType associated with this browser.
     */
    @Override
    public IBrowserType getBrowserType() {
        return this.browserType;
    }

    /**
     * Gets the bounding rectangle for an element.
     *
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    @Override
    public ClientRects getClientRects(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.getClientRects));");
        ArrayList rects = (ArrayList) executeScript(element.getSelector().toJQuery().toString(JQueryStringType.GET_CLIENT_RECTS));
        int bottom = ((Number) rects.get(1)).intValue();
        int left = ((Number) rects.get(2)).intValue();
        int right = ((Number) rects.get(3)).intValue();
        int top = ((Number) rects.get(4)).intValue();
        return new ClientRects(top, bottom, left, right);
    }

    /**
     * Sends a non-alphanumeric keys to an element.
     *
     * @param element The element to recieve the keys.
     * @param key     The key to be sent.
     */
    @Override
    public void pressKeyboardKey(WebControl element, KeyboardKey key) {
        ((SeleniumElement) element).getUnderlyingWebElement().sendKeys(Keys.getKeyFromUnicode(key.getUnicode()));
    }

    /**
     * Checks if a window does not exist by the title.
     *
     * @param windowTitle The title of the window to check for.
     * @return The title of the window.
     */
    @Override
    public String windowDoesNotExistByTitle(String windowTitle) {
        if (windowTitle.isEmpty()) {
            throw new IllegalArgumentException("window title is invalid");
        }
        try {
            switchToWindowByTitle(windowTitle);
            throw new WindowExistsException(windowTitle);
        } catch (NoSuchWindowException e) {
            return windowTitle;
        }
    }

    /**
     * Checks if a window does not exist by the url.
     *
     * @param url The url of the window to check for.
     * @return The url of the window.
     */
    @Override
    public String windowDoesNotExistByUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("window title is invalid");
        }
        try {
            switchToWindowByUrl(url);
            throw new WindowExistsException(url);
        } catch (NoSuchWindowException e) {
            return url;
        }
    }

    @Override
    public void set(WebControl control, WebSelectOption option, String setValue) {
        String tag = getElementTagName(control).toLowerCase();

        switch (tag) {
            case SELECT:
                switch (option) {
                    case VALUE:
                        chooseSelectElementByValue(control, setValue);
                        break;
                    case TEXT:
                        chooseSelectElementByText(control, setValue);
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }

                break;
            case "textarea":
                clickElement(control);
                clearElement(control);
                sendKeysToElement(control, setValue);
                break;
            default:
                String currentValue = getElementAttribute(control, VALUE);
                if (currentValue != null) {
                    StringBuilder backspaces = new StringBuilder();
                    for (int i = 0; i < currentValue.length(); i++) {
                        backspaces.append(Keys.BACK_SPACE);
                    }
                    sendKeysToElement(control, Keys.END + backspaces.toString());
                }

                sendKeysToElement(control, setValue);
                break;
        }
    }

    @Override
    public void selectFile(WebControl control, String path) {
        if (!isRemote && browserType.equals(BrowserType.INTERNET_EXPLORER)) {

            WebElement underlyingWebElement = ((SeleniumElement) control).getUnderlyingWebElement();
            Point webElementLocation = underlyingWebElement.getLocation();
            Dimension elementSize = underlyingWebElement.getSize();
            int x = webElementLocation.getX() + elementSize.width / 2;
            int y = webElementLocation.getY() + elementSize.height / 2;

            // Get the current coordinates
            java.awt.Point pointLocation = MouseInfo.getPointerInfo().getLocation();
            int xOriginal = (int) pointLocation.getX();
            int yOriginal = (int) pointLocation.getY();

            try {
                Robot mouseRobot = new Robot();
                mouseRobot.mouseMove(x, y);
                mouseRobot.mousePress(InputEvent.BUTTON1_MASK);
                mouseRobot.mouseRelease(InputEvent.BUTTON1_MASK);

                //return mouse to original position
                mouseRobot.mouseMove(xOriginal, yOriginal);
            } catch (AWTException e) {
                log.error("Error clicking via Robot.", e);
            }

            try {
                SendKeysHelper.sendKeysToKeyboard(path);
                SendKeysHelper.sendEnterKey();
            } catch (java.awt.AWTException e) {
                throw new UnableToSendKeysException(e);
            }
        } else {
            sendKeysToElement(control, path);
        }
    }

    /**
     * Sets the action factory.
     *
     * @param actionsFactory The action factory.
     */
    void setActionsFactory(ActionsFactory actionsFactory) {
        this.actionsFactory = actionsFactory;
    }

    /**
     * Sets the file download helper.
     *
     * @param fileDownloadHelper The file download helper.
     */
    void setFileDownloadHelper(FileDownloadHelper fileDownloadHelper) {
        this.fileDownloadHelper = fileDownloadHelper;
    }

    private void collectSeleniumLogs() {
        long timeNow = System.currentTimeMillis();
        loggingPreferences.getEnabledLogTypes().forEach(logType -> {
            String filename = String.format("%s/%s-%d.log", seleniumLogsDirectory, logType, timeNow);
            try {
                List<LogEntry> logEntries = webDriver.manage().logs().get(logType).getAll();
                List<String> logStrings = logEntries.stream().map(logEntry -> logEntry.toJson().toString()).collect(Collectors.toList());
                List<Map<String, Object>> logMapList = logEntries.stream().map(logEntry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("timestamp", logEntry.getTimestamp());
                    map.put("level", logEntry.getLevel().toString());
                    map.put("message", logEntry.getMessage());
                    return map;
                }).collect(Collectors.toList());

                AeonTestExecution.executionEvent(logType + "LogsCollected", logMapList);
                writingToLog(filename, logStrings);
            } catch (Exception e) {
                log.info("The log type \"{}\" is either not supported or does not exist in this context.", logType);
            }
        });
    }

    private void writingToLog(String filename, List<String> logStrings) throws IOException {
        File file = new File(filename);
        file.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(file);
        try {
            for (String logString : logStrings) {
                fileWriter.write(logString);
                fileWriter.write('\n');
            }
        } catch (IOException e) {
            log.error("Couldn't write Selenium log entries to " + filename, e);
        } finally {
            fileWriter.close();
        }
    }

    private boolean osIsMacOrLinux() {
        return OsCheck.getOperatingSystemType().equals(OsCheck.OSType.MAC_OS)
                || OsCheck.getOperatingSystemType().equals(OsCheck.OSType.LINUX);
    }
}
