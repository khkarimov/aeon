package aeon.selenium;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.exceptions.ElementNotVisibleException;
import aeon.core.common.exceptions.NoSuchElementException;
import aeon.core.common.exceptions.NoSuchWindowException;
import aeon.core.common.helpers.*;
import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.*;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.testabstraction.models.Browser;
import aeon.core.testabstraction.product.AeonTestExecution;
import aeon.selenium.jquery.IJavaScriptFlowExecutor;
import aeon.selenium.jquery.SeleniumScriptExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static aeon.core.common.helpers.DateTimeExtensions.approximatelyEquals;
import static aeon.core.common.helpers.StringUtils.like;
import static aeon.core.common.helpers.StringUtils.normalizeSpacing;

/**
 * Web adapter for Selenium.
 */
public class SeleniumAdapter implements IWebAdapter, AutoCloseable {

    private final URL seleniumHubUrl;
    protected WebDriver webDriver;
    private IJavaScriptFlowExecutor javaScriptExecutor;
    private boolean moveMouseToOrigin;
    protected BrowserType browserType;
    private static Logger log = LogManager.getLogger(SeleniumAdapter.class);
    private boolean isRemote;
    protected String seleniumLogsDirectory;
    protected LoggingPreferences loggingPreferences;
    protected BrowserSize fallbackBrowserSize;

    /**
     * Constructor for Selenium Adapter.
     *
     * @param seleniumWebDriver     The driver for the adapter.
     * @param javaScriptExecutor    The javaScript executor for the adapter.
     * @param moveMouseToOrigin     A boolean indicating whether or not the mouse will return to the origin
     *                              (top left corner of the browser window) before executing every action.
     * @param browserType           The browser type for the adapter.
     * @param fallbackBrowserSize   The size the browser will be maximized to.
     * @param isRemote              Whether we are testing remotely or locally.
     * @param seleniumHubUrl        The used Selenium hub URL.
     * @param seleniumLogsDirectory The path to the directory for Selenium Logs
     * @param loggingPreferences    Preferences which contain which Selenium log types to enable
     */
    public SeleniumAdapter(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, boolean moveMouseToOrigin, BrowserType browserType, BrowserSize fallbackBrowserSize, boolean isRemote, URL seleniumHubUrl, String seleniumLogsDirectory, LoggingPreferences loggingPreferences) {
        this.javaScriptExecutor = javaScriptExecutor;
        this.webDriver = seleniumWebDriver;
        this.moveMouseToOrigin = moveMouseToOrigin;
        this.browserType = browserType;
        this.isRemote = isRemote;
        this.seleniumHubUrl = seleniumHubUrl;
        this.seleniumLogsDirectory = seleniumLogsDirectory;
        this.loggingPreferences = loggingPreferences;
        this.fallbackBrowserSize = fallbackBrowserSize;
    }

    /**
     * Gets the web driver.
     *
     * @return The web driver is returned.
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    private void setWebDriver(WebDriver value) {
        webDriver = value;
    }

    /**
     * Gets the java script executor.
     *
     * @return The java script executor is returned.
     */
    protected final IJavaScriptFlowExecutor getJavaScriptExecutor() {
        return javaScriptExecutor;
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public final void addCookie(IWebCookie cookie) {
        log.trace("WebDriver.add_Cookie();");

        // TODO(FrankS) : needed for bug in gecko driver. https://bugzilla.mozilla.org/show_bug.cgi?id=1415828
        String domain = cookie.getDomain();
        if (browserType == BrowserType.Firefox && cookie.getDomain().charAt(0) == '.') {
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

        log.trace(String.format("Result: %1$s", result));
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
            throw new aeon.core.common.exceptions.NoSuchCookieException(name);
        }

        IWebCookie result = new SeleniumCookie(cookie);
        log.trace(String.format("Result: %1$s", result));
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
            throw new aeon.core.common.exceptions.NoSuchCookieException(name);
        }

        // Delete old cookie, then add a new one with the new value.
        getWebDriver().manage().deleteCookieNamed(name);

        // TODO(FrankS) : needed for bug in gecko driver. https://bugzilla.mozilla.org/show_bug.cgi?id=1415828
        String domain = cookie.getDomain();
        if (browserType == BrowserType.Firefox && cookie.getDomain().charAt(0) == '.') {
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
        log.trace(String.format("Result: %1$s", result));
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
        log.trace(String.format("Result: %1$s", result));
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
        log.trace(String.format("Result: %1$s", result));
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
        if (waitForAllPopupWindowsToClose) {
            if (getWindowHandles().size() > 1) {
                throw new NotAllPopupWindowsClosedException();
            }
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
        log.trace(String.format("Result: %1$s", result));
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
        log.trace(String.format("WebDriver.Navigate().goToUrl(\"%1$s\");", url));

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
        aeon.core.common.web.selectors.By by =
                (aeon.core.common.web.selectors.By)
                        ((findBy instanceof aeon.core.common.web.selectors.By) ? findBy : null);

        if (by != null) {
            log.trace(String.format("WebDriver.findElement(by.cssSelector(%1$s));", by));
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.cssSelector(findBy.toString())));
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementException(e, by);
            }
        }

        ByJQuery byJQuery = (ByJQuery) ((findBy instanceof ByJQuery) ? findBy : null);
        if (byJQuery != null) {
            return (WebControl) findElements(byJQuery).toArray()[0];
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
        aeon.core.common.web.selectors.By by = (aeon.core.common.web.selectors.By) ((findBy instanceof aeon.core.common.web.selectors.By) ? findBy : null);
        if (by != null) {
            Collection<WebControl> collection;
            log.trace(String.format("WebDriver.findElements(by.cssSelector(%1$s));", by));

            try {
                collection = webDriver.findElements(org.openqa.selenium.By.cssSelector(findBy.toString()))
                        .stream()
                        .map(SeleniumElement::new)
                        .collect(Collectors.toList());
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementsException(e, by);
            }

            if (collection.size() == 0) {
                throw new NoSuchElementsException(by);
            }

            return collection;
        }

        ByJQuery byJQuery = (ByJQuery) ((findBy instanceof ByJQuery) ? findBy : null);
        if (byJQuery != null) {
            try {
                return findElements(byJQuery);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementsException(e, byJQuery);
            }
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
        String script = findBy.toString(JQueryStringType.ReturnElementArray);
        Object result = executeScript(script);

        if (result instanceof Collection<?> && ((Collection<?>) result).size() == 0) {
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
        SeleniumElement elem = (SeleniumElement) ((webElement instanceof SeleniumElement) ? webElement : null);
        if (elem == null) {
            throw new UnsupportedSelectElementException(webElement.getClass());
        }

        return new SeleniumSelectElement(new Select(elem.getUnderlyingWebElement()));
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
        SeleniumElement elem = (SeleniumElement) ((webElement instanceof SeleniumElement) ? webElement : null);
        if (elem == null) {
            throw new UnsupportedElementException(webElement.getClass());
        }

        log.trace("WebDriver.SwitchTo().Frame(<IWebElement>);");

        webDriver.switchTo().frame(elem.getUnderlyingWebElement());
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
            log.trace(String.format("WebDriver.SwitchTo().Window(%1$s);", window));

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
        log.trace(String.format("WebDriver.SwitchTo().Window(%1$s);", handle));
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
            throw new IllegalArgumentException("value");
        }

        for (String window : getWindowHandles()) {
            log.trace(String.format("WebDriver.SwitchTo().Window(%1$s);", window));

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
                browserType == BrowserType.Chrome
                        || browserType == BrowserType.Edge
                        || browserType == BrowserType.Firefox
                        || browserType == BrowserType.Opera
                        || browserType == BrowserType.Safari
                        || browserType == BrowserType.InternetExplorer
        )) {

            String sessionId = ((RemoteWebDriver) webDriver).getSessionId().toString();

            collectSeleniumLogs();

            webDriver.quit();

            String videoPath = downloadVideo(sessionId);

            if (videoPath != null) {
                AeonTestExecution.executionEvent("videoDownloaded", videoPath);
            }

            return;
        }

        collectSeleniumLogs();

        webDriver.quit();
    }

    private String downloadVideo(String sessionId) {

        URL downloadUrl;
        try {
            downloadUrl = new URL(
                    seleniumHubUrl.getProtocol(),
                    seleniumHubUrl.getHost(),
                    seleniumHubUrl.getPort(),
                    "/grid/admin/HubVideoDownloadServlet?sessionId=" + sessionId);
        } catch (MalformedURLException e) {
            log.error("Error creating video download URL", e);

            return null;
        }

        File tempFile;
        try {
            // Check for correct response and content type for graceful failure
            HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            connection.disconnect();
            // The actual file is webm, but connection grabs it as mp4
            if (!contentType.equals("video/mp4")) {
                log.trace("Test video not downloaded: Either this grid does not support video, or the given sessionId is invalid.");

                return null;
            }

            tempFile = File.createTempFile("video-", ".webm");
            tempFile.deleteOnExit();

        } catch (IOException e) {
            log.error("Error checking for response type for video download from Selenium Grid.", e);

            return null;
        }

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(downloadUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile);) {

            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            log.error("Error downloading video from Selenium Grid.", e);

            return null;
        }

        log.info(String.format("Video downloaded from Selenium Grid: %s", tempFile.getAbsolutePath()));

        return tempFile.getAbsolutePath();
    }

    /**
     * Verifies there is an alert raised on the page.
     */
    public final void verifyAlertExists() {
        log.trace("WebDriver.SwitchTo().Alert();");

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
        log.trace("WebDriver.SwitchTo().Alert();");

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
            log.trace(String.format("WebDriver.SwitchTo().Alert().sendKeys(%1$s);", keysToSend));
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
            log.trace("WebDriver.SwitchTo().Alert();");
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
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.BlurElement));
    }

    /**
     * Maximizes the browser window.
     * <p>
     * Workaround implemented for chromiun browsers running in MacOS due to maximize default behaviour
     * only expanding vertically. More information can be found at:
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=985
     * Also problems with maximize in chrome 60:
     * https://bugs.chromium.org/p/chromedriver/issues/detail?id=1901
     */
    public void maximize() {
        try {
            log.trace("Webdriver.Manage().Window.maximize();");
            if (isRemote && (browserType.equals(BrowserType.Chrome) ||
                    browserType.equals(BrowserType.Firefox) ||
                    browserType.equals(BrowserType.Opera))) {
                java.awt.Dimension dimension = BrowserSizeMap.map(fallbackBrowserSize);
                log.trace("Setting manual size  for remote test on chrome, firefox, or opera.");
                webDriver.manage().window().setPosition(new Point(0, 0));
                webDriver.manage().window().setSize(new Dimension(dimension.width, dimension.height));
            } else if (!isRemote && osIsMacOrLinux() && (browserType.equals(BrowserType.Opera) ||
                        browserType.equals(BrowserType.Chrome))) {
                int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                Point position = new Point(0, 0);
                webDriver.manage().window().setPosition(position);
                Dimension maximizedScreenSize =
                        new Dimension(screenWidth, screenHeight);
                log.trace(String.format("Using maximize workaround on local Mac or Linux machines with resolution %s", maximizedScreenSize));
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
        log.trace(String.format("WebDriver.Manage().Window.set_Size(%1$s);", size));
        webDriver.manage().window().setSize(new Dimension(size.width, size.height));
    }

    /**
     * Clicks on a web control.
     *
     * @param element           The element to click on.
     * @param moveMouseToOrigin Whether to move the mouse to the top left corner before clicking or not.
     */
    protected void click(WebControl element, boolean moveMouseToOrigin) {
        ((SeleniumElement) element).click(moveMouseToOrigin);
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
        executeScript(selector.toJQuery().toString(JQueryStringType.ScrollElementIntoView));
        executeScript("$(\"body\").scrollLeft(0);");
    }

    /**
     * Returns a screenshot of the current browser window.
     *
     * @return An Image object of the current browser.
     */
    public Image getScreenshot() {
        TakesScreenshot driver = (TakesScreenshot) ((webDriver instanceof TakesScreenshot) ? webDriver : null);

        if (driver == null) {
            throw new IllegalStateException("Web IDriver does not support taking screenshot");
        }

        log.trace("webDriver.getScreenshotAs(bytes);");

        byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            throw new UnableToTakeScreenshotException();
        }
    }

    /**
     * Gets the source code of the page (HTML).
     *
     * @return A string representation of the source code of the page.
     */
    public final String getPageSource() {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

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
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }
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
            Actions builder = new Actions(webDriver);
            builder.clickAndHold(draggable).perform();
            Sleep.wait(250);

            if (browserType == BrowserType.Firefox) {
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
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }
        log.trace("new Actions(IWebDriver).ContextClick(IWebElement);");

        if (browserType == BrowserType.Firefox || browserType == BrowserType.InternetExplorer) {
            scrollElementIntoView(element);
        }
        (new Actions(webDriver)).contextClick(
                ((SeleniumElement) element).getUnderlyingWebElement())
                .perform();
    }

    // work around for marionette driver v.11.1

    /**
     * Performs a rightClick on the element passed as an argument by executing javascript.
     *
     * @param element The element to perform the rightClick on.
     */
    public final void rightClickByJavaScript(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.ShowContextMenu));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.ShowContextMenu));
    }

    /**
     * Performs a doubleClick on the element passed as an argument.
     *
     * @param element The element to perform the doubleClick on.
     */
    public final void doubleClick(WebControl element) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }
        if (this.browserType == BrowserType.Firefox) {
            doubleClickByJavaScript(element);
            return;
        }

        if (this.browserType == BrowserType.InternetExplorer) {
            scrollElementIntoView(element);
        }
        log.trace("new Actions(IWebDriver).doubleClick(IWebElement);");

        (new Actions(webDriver)).doubleClick(
                ((SeleniumElement) element).getUnderlyingWebElement())
                .perform();
    }

    // work around for marionette driver v.11.1

    /**
     * Performs a doubleClick on the element passed as an argument by executing javascript.
     *
     * @param element The element to perform the doubleClick on.
     */
    public final void doubleClickByJavaScript(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.FireDoubleClick));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.FireDoubleClick));
    }

    /**
     * Performs a LeftClick on the element passed as an argument.
     *
     * @param element The element to perform the click on.
     */
    public void click(WebControl element) {
        // TODO(patricka) Check whether the wrapped click is still necessary.
        // check if wrapped (Might affect three browsers)
        /*String script = String.format(
                "var rects = %1$s[0].getClientRects(); return rects.length;", element.getSelector().toJQuery());

        long rectsLength = (long) executeScript(script);

        if (rectsLength > 1) {
            script = String.format(
                    "var rects = %1$s[0].getClientRects(); var arr = [rects[0].left, rects[0].top, rects[0].right, rects[0].bottom]; return arr;",
                    element.getSelector().toJQuery());

            Collection<Object> list = (Collection<Object>) executeScript(script);

            // (abstract/virtual)<--(depends on whether the class is to be made abstract or not) method to define the way the wrapping should be handled per browser
            wrappedClick(element, new ArrayList<>(list));
        } else {*/
        click(element, moveMouseToOrigin);
        //}
    }

    // Linked to selenium issue https://code.google.com/p/selenium/issues/detail?id=6702 and https://code.google.com/p/selenium/issues/detail?id=4618

    /**
     * Method to define the way the wrapping should be handled per browser. Clicks in the middle
     * of the coordinates provided in the list.
     *
     * @param element The element to click.
     * @param list    The list of coordinates.
     */
    protected void wrappedClick(WebControl element, List<Object> list) {
        int x1 = (int) (list.get(0));
        int y1 = (int) (list.get(1));
        int x2 = (int) (list.get(2));
        int y2 = (int) (list.get(3));

        int x = (x2 - x1) / 2;
        int y = (y2 - y1) / 2;

        SeleniumElement seleniumElement = (SeleniumElement) element;

        (new Actions(webDriver))
                .moveToElement(seleniumElement.getUnderlyingWebElement(), x, y)
                .click()
                .perform();
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

        (new Actions(webDriver))
                .moveToElement(seleniumElement.getUnderlyingWebElement(), x, y)
                .click()
                .perform();
    }

    /**
     * Refreshes the current frame.
     */
    public final void refreshFrame() {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

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
        Actions action = new Actions(webDriver);

        // click.
        if (browserType == BrowserType.Firefox || browserType == BrowserType.InternetExplorer) {
            scrollElementIntoView(seleniumElement);
        }

        action.clickAndHold(seleniumElement.getUnderlyingWebElement()).perform();

        // Hold the click.
        Sleep.wait(duration);

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
            throw new ElementIsEnabledException();
        }
    }

    /**
     * Checks that an element is enabled.
     *
     * @param element The web element to check.
     */
    public void isElementEnabled(WebControl element) {
        boolean enabled = ((SeleniumElement) element).enabled();
        if (!enabled) {
            throw new ElementNotEnabledException();
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
        //IF execution reachd here then element exists.
        throw new ElementExistsException();
    }

    private void hasOptions(SeleniumElement element, String[] options, WebSelectOption select) {
        try {
            for (String desiredOption : options) {
                if (select == WebSelectOption.Text) {
                    element.findElementByXPath(aeon.core.common.web.selectors.By.cssSelector(".//option[normalize-space(.) = " + Quotes.escape(desiredOption) + "]"));
                } else {
                    element.findElement(aeon.core.common.web.selectors.By.cssSelector("option[value='".concat(desiredOption).concat("']")));
                }
            }
        } catch (NoSuchElementException e) {
            throw new ElementDoesNotHaveOptionException(e.toString());
        }
    }

    private void doesNotHaveOptions(SeleniumElement element, String[] options, WebSelectOption select) {
        for (String desiredOption : options) {
            boolean elementFound = true;
            try {
                if (select == WebSelectOption.Text) {
                    element.findElementByXPath(aeon.core.common.web.selectors.By.cssSelector(".//option[normalize-space(.) = " + Quotes.escape(desiredOption) + "]"));
                } else {
                    element.findElement(aeon.core.common.web.selectors.By.cssSelector("option[value='".concat(desiredOption).concat("']")));
                }
            } catch (NoSuchElementException e) {
                elementFound = false;
            }
            if (elementFound) {
                throw new ElementHasOptionException(desiredOption);
            }
        }
    }

    /**
     * Asserts that a select has all the options specified. Optionally can specify which option group the elements are part of.
     *
     * @param element  The select element which should contain the options.
     * @param options  The options.
     * @param optgroup The optional option group.
     * @param select   The method by which the options are identifed, either their value or their visible text.
     */
    public void elementHasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).getTagName(), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(aeon.core.common.web.selectors.By.cssSelector("optgroup[label='".concat(optgroup).concat("']")));
            hasOptions(group, options, select);
        } else {
            hasOptions((SeleniumElement) element, options, select);
        }
    }

    /**
     * Asserts that a select element has none of the options specified. Optionally can specify which option group the elements are part of.
     *
     * @param element  The select element which should not contain the options.
     * @param options  The options.
     * @param optgroup The optional option group.
     * @param select   The method by which the options are identified, either their value or their visible text.
     */
    public void elementDoesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).getTagName(), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(aeon.core.common.web.selectors.By.cssSelector("optgroup[label='".concat(optgroup).concat("']")));
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
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.MouseOut));
    }

    /**
     * Moves the mouse pointer over an element.
     *
     * @param element The element to move the mouse pointer over.
     */
    @Override
    public void mouseOver(WebControl element) {
        log.trace("executeScript(element.getSelector().toJQuery().toString(JQueryStringType.mouseOver));");
        executeScript(element.getSelector().toJQuery().toString(JQueryStringType.MouseOver));
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
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SetBodyText), Quotes.escape(value)));
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
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SetElementText), Quotes.escape(value)));
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
        executeScript(String.format(element.getSelector().toJQuery().toString(JQueryStringType.SetDivText), Quotes.escape(value)));
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
     * Asserts that a select has all the specified elements in that order. Can optionally specify which optiongroup the elements are a part of.
     *
     * @param element  The select element to search.
     * @param options  The options the element should have in the same order.
     * @param optgroup The option group the options should be a part of.
     * @param select   The method by which the options are identifed, either their value, or their visible text.
     */
    public void elementHasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).getTagName().equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).getTagName(), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).findElement(aeon.core.common.web.selectors.By.cssSelector("optgroup[label='" + optgroup + "']"));
            elementHasOptionsInOrder(group, options, select);
        } else {
            elementHasOptionsInOrder((SeleniumElement) element, options, select);
        }
    }

    private void elementHasOptionsInOrder(SeleniumElement element, String[] options, WebSelectOption select) {
        try {
            if (options.length > 1) {
                if (select == WebSelectOption.Text) {
                    element = (SeleniumElement) element.findElementByXPath(aeon.core.common.web.selectors.By.cssSelector(".//option[normalize-space(.) = " + Quotes.escape(options[0]) + "]"));
                }

                for (int i = 1; i < options.length; i++) {
                    if (select == WebSelectOption.Value) {
                        element.findElement(aeon.core.common.web.selectors.By.cssSelector("option[value='" + options[i - 1] + "'] ~ option[value='" + options[i] + "']"));
                    } else {
                        element = (SeleniumElement) element.findElementByXPath(aeon.core.common.web.selectors.By.cssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[i]) + "]"));
                    }
                }
            } else {
                if (select == WebSelectOption.Value) {
                    element.findElement(aeon.core.common.web.selectors.By.cssSelector("option[value='" + options[0] + "']"));
                } else {
                    element.findElementByXPath(aeon.core.common.web.selectors.By.cssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[0]) + "]"));
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException | aeon.core.common.exceptions.NoSuchElementException e) {
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
        if (!((SeleniumElement) element).getTagName().equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).getTagName(), "select");
        }
        if (optgroup != null) {
            element = ((SeleniumElement) element).findElement(aeon.core.common.web.selectors.By.cssSelector("optgroup[label='" + optgroup + "']"));
        }
        Collection<WebControl> options = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector("option"));
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
     * @param optGroup An optional option group which would be searched in isolation instad of all the options under select.
     */
    public void hasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup) {
        if (optGroup != null) {
            element = ((SeleniumElement) element).findElement(aeon.core.common.web.selectors.By.cssSelector("optgroup[label='" + optGroup + "']"));
        }
        Collection<WebControl> elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector("option"));
        Iterator<WebControl> elementsIterator = elements.iterator();
        SeleniumElement prevOption = (SeleniumElement) elementsIterator.next();
        SeleniumElement currOption;
        while (elementsIterator.hasNext()) {
            currOption = (SeleniumElement) elementsIterator.next();
            switch (compare) {
                case AscendingByText:
                    if (prevOption.getText().toLowerCase().compareTo(currOption.getText().toLowerCase()) > 0) {
                        throw new ElementsNotInOrderException(compare);
                    }
                    break;
                case DescendingByText:
                    if (prevOption.getText().toLowerCase().compareTo(currOption.getText().toLowerCase()) < 0) {
                        throw new ElementsNotInOrderException(compare);
                    }
                    break;
                case AscendingByValue:
                    if (prevOption.getAttribute("value").toLowerCase().compareTo(currOption.getAttribute("value")) > 0) {
                        throw new ElementsNotInOrderException(compare);
                    }
                    break;
                case DescendingByValue:
                    if (prevOption.getAttribute("value").toLowerCase().compareTo(currOption.getAttribute("value")) < 0) {
                        throw new ElementsNotInOrderException(compare);
                    }
                    break;
            }
            prevOption = currOption;
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
            throw new ElementNotVisibleException();
        }
    }

    /**
     * Checks to see if a selected element is hidden.
     *
     * @param element The select element.
     */
    public void notVisible(WebControl element) {
        if (((SeleniumElement) element).displayed()) {
            throw new ElementIsVisibleException();
        }
    }


    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText())).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute))).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute))).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String value : values) {
                if (!elements.contains(value)) {
                    throw new ElementDoesNotHaveException(value);
                }
            }
        }
    }

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * Comparisons are made ignoring whitespace and case.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void hasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(x -> normalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> aeon.core.common.helpers.StringUtils.
                        normalizeSpacing(((SeleniumElement) e).getText()).toLowerCase()).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> aeon.core.common.helpers.StringUtils.
                        normalizeSpacing(((SeleniumElement) e).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> aeon.core.common.helpers.StringUtils.
                            normalizeSpacing(((SeleniumElement) x).getAttribute(attribute).toLowerCase())).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String value : values) {
                if (!elements.contains(value)) {
                    throw new ElementDoesNotHaveException(value);
                }
            }
        }
    }

    /**
     * Asserts that an elements children do not posses a text.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the chilren should not posses.
     * @param selector  The selector for the children to be searched.
     * @param option    The comparison option to be compared.
     * @param attribute The string attribute to get.
     */
    public void doesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText())).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute))).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute))).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String value : values) {
                if (elements.contains(value)) {
                    throw new ElementHasException(value);
                }
            }
        }
    }

    /**
     * Asserts that an elements children do not posses a text. Comparisons made ignoring case and whitespace.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the chilren should not posses.
     * @param selector  The selector for the children to be searched.
     * @param option    The comparison option to be compared.
     * @param attribute The string attribute to get.
     */
    public void doesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(x -> normalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText()).toLowerCase()).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector(selector))
                    .stream().map(x -> normalizeSpacing(((SeleniumElement) x).getAttribute(attribute)).toLowerCase()).collect(Collectors.toList());
        }
        if (elements != null) {
            for (String value : values) {
                if (elements.contains(value)) {
                    throw new ElementHasException(value);
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
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void hasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.stream(messages).map(StringUtils::normalizeSpacing).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getText())).collect(Collectors.toList());
            } else {
                elements = ((SeleniumElement) element).
                        findElements(aeon.core.common.web.selectors.By.cssSelector(selector)).
                        stream().map(e -> normalizeSpacing(((SeleniumElement) e).getAttribute(attribute))).collect(Collectors.toList());
            }
        } else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).findElements(aeon.core.common.web.selectors.By.cssSelector(selector))
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
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT") && (attribute.equalsIgnoreCase("INNERHTML") || attribute.equalsIgnoreCase("VALUE"))) {
            isWithSelect(element, expectedValue, attribute);
            return;
        }
        // If Text option was selected then use getText, otherwise use getAttribute
        if (option == ComparisonOption.Text) {
            if (StringUtils.is(expectedValue, ((SeleniumElement) element).getText())) {
                throw new ValuesAreNotEqualException(((SeleniumElement) element).getText(), expectedValue);
            }
        } else {
            if (StringUtils.is(expectedValue, ((SeleniumElement) element).getAttribute(attribute))) {
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
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT")) {
            throw new UnsupportedElementException(element.getClass());
        }
        if (attribute.equalsIgnoreCase("INNERHTML")) {
            String value = ((SeleniumElement) element).getSelectedOptionText();
            if (StringUtils.is(value, expectedValue)) {
                throw new ValuesAreNotEqualException(value, expectedValue);
            }
        } else {
            String value = getElementAttribute(((SeleniumElement) element).getSelectedOption(), attribute);
            if (StringUtils.is(value, expectedValue)) {
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
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT") && (attribute.equalsIgnoreCase("INNERHTML") || attribute.equalsIgnoreCase("VALUE"))) {
            isLikeWithSelect(element, value, attribute);
            return;
        }
        if (option == ComparisonOption.Text) {
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
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT")) {
            throw new UnsupportedElementException(element.getClass());
        }
        if (attribute.equalsIgnoreCase("INNERHTML")) {
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
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    @Override
    public void isNotLike(WebControl element, String value, ComparisonOption option, String attribute) {
        if (((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT") && (attribute.equalsIgnoreCase("INNERHTML") || attribute.equalsIgnoreCase("VALUE"))) {
            isNotLikeWithSelect(element, value, attribute);
            return;
        }
        if (option == ComparisonOption.Text && value.toUpperCase().equals("INNERHTML")) {
            if (like(value, ((SeleniumElement) element).getText(), false)) {
                throw new ValuesAreAlikeException(value, ((SeleniumElement) element).getText());
            }
        } else {
            if (like(value, ((SeleniumElement) element).getAttribute(attribute), false)) {
                throw new ValuesAreAlikeException(value, ((SeleniumElement) element).getAttribute(attribute));
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
        if (!((SeleniumElement) element).getTagName().equalsIgnoreCase("SELECT")) {
            throw new UnsupportedElementException(element.getClass());
        }
        try {
            isLikeWithSelect(element, expectedValue, attribute);
        } catch (ValuesAreNotAlikeException e) {
            return; // that means the values are not alike
        }
        if (attribute.equalsIgnoreCase("INNERHTML")) { // this is to make sure the correct exception is thrown
            throw new ValuesAreAlikeException(expectedValue, ((SeleniumElement) element).getSelectedOptionText());
        } else {
            throw new ValuesAreAlikeException(expectedValue, getElementAttribute(((SeleniumElement) element).getSelectedOption(), attribute));
        }
    }

    @Override
    public void verifyAlertText(String comparingText) {
        if (StringUtils.is(getAlertText(), comparingText)) {
            throw new ValuesAreNotEqualException(getAlertText(), comparingText);
        }
    }

    @Override
    public void verifyAlertTextLike(String comparingText, boolean caseSensitive) {
        if (!like(getAlertText(), comparingText, caseSensitive)) {
            throw new ValuesAreNotAlikeException(comparingText, getAlertText());
        }
    }

    @Override
    public void verifyTitle(String comparingTitle) {
        if (StringUtils.is(getTitle(), comparingTitle)) {
            throw new ValuesAreNotEqualException(getTitle(), comparingTitle);
        }
    }

    @Override
    public void verifyURL(URL comparingURL) {

        URL url = getUrl();
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        if (comparingURL == null) {
            throw new IllegalArgumentException("comparingURL");
        }

        if (!url.equals(comparingURL)) {
            throw new ValuesAreNotEqualException(url.toString(), comparingURL.toString());
        }
    }

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. Has a
     * Margin of error. The date must be in the ISO-8601 standard.
     *
     * @param element       The element that posseses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expectedDate  The expected date that the attribute should posses.
     * @param delta         The margin of error that the date can be within. Cannot posses any weeks, months or years due to
     *                      them having variable lengths.
     */
    @Override
    public void datesApproximatelyEqual(WebControl element, String attributeName, DateTime expectedDate, Period delta) {
        String actualString = ((SeleniumElement) element).getAttribute(attributeName);
        try {
            DateTime actualDate = DateTime.parse(actualString);
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
    public BrowserType getBrowserType() {
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
        ArrayList rects = (ArrayList) executeScript(element.getSelector().toJQuery().toString(JQueryStringType.GetClientRects));
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
        String tag = getElementTagName(control).toUpperCase();    //driver.getElementTagName(control).toUpperCase();

        switch (tag) {
            case "SELECT":
                switch (option) {
                    case Value:
                        chooseSelectElementByValue(control, setValue);
                        break;
                    case Text:
                        chooseSelectElementByText(control, setValue);
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }

                break;
            case "TEXTAREA":
                clickElement(control);
                clearElement(control);
                sendKeysToElement(control, setValue);
                break;
            default:
                String currentValue = getElementAttribute(control, "value");
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
//        if (!isRemote && browserType.equals(BrowserType.InternetExplorer)) {
//
//            WebElement underlyingWebElement = ((SeleniumElement) control).getUnderlyingWebElement();
//            Point webElementLocation = underlyingWebElement.getLocation();
//            Dimension elementSize = underlyingWebElement.getSize();
//            int x = webElementLocation.getX() + elementSize.width / 2;
//            int y = webElementLocation.getY() + elementSize.height / 2;
//            MouseHelper.click(x, y);
//
//            try {
//                SendKeysHelper.sendKeysToKeyboard(path);
//                SendKeysHelper.sendEnterKey();
//            } catch (AWTException e) {
//                log.error(e.getMessage());
//                throw new RuntimeException(e);
//            }
//        } else {
            sendKeysToElement(control, path);
        //}
    }

    private void collectSeleniumLogs() {
        long timeNow = System.currentTimeMillis();
        loggingPreferences.getEnabledLogTypes().forEach(logType -> {
            String filename = String.format("%s/%s-%d.log", seleniumLogsDirectory, logType, timeNow);
            try {
                List<LogEntry> logEntries = webDriver.manage().logs().get(logType).getAll();
                List<String> logStrings = logEntries.stream().map(log -> log.toJson().toString()).collect(Collectors.toList());
                List<Map<String, Object>> logMapList = logEntries.stream().map(log -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("timestamp", log.getTimestamp());
                    map.put("level", log.getLevel().toString());
                    map.put("message", log.getMessage());
                    return map;
                }).collect(Collectors.toList());

                AeonTestExecution.executionEvent(logType + "LogsCollected", logMapList);

                try {
                    File file = new File(filename);
                    file.getParentFile().mkdirs();
                    FileWriter fileWriter = new FileWriter(file);
                    for (String log : logStrings) {
                        fileWriter.write(log);
                        fileWriter.write('\n');
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    log.error("Couldn't write Selenium log entries to " + filename, e);
                }
            } catch (Exception e) {
                log.info("The log type \"" + logType + "\" is either not supported or does not exist in this context.");
            }
        });
    }

    private boolean osIsMacOrLinux() {
        return OsCheck.getOperatingSystemType().equals(OsCheck.OSType.MacOS)
                || OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Linux);
    }
}

