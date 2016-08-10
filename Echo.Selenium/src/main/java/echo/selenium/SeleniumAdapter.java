package echo.selenium;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.ui.Size;
import echo.core.common.CompareType;
import echo.core.common.ComparisonOption;
import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.*;
import echo.core.common.exceptions.ElementNotVisibleException;
import echo.core.common.exceptions.NoSuchElementException;
import echo.core.common.exceptions.NoSuchWindowException;
import echo.core.common.helpers.SendKeysHelper;
import echo.core.common.helpers.Sleep;
import echo.core.common.logging.ILog;
import echo.core.common.web.BrowserType;
import echo.core.common.web.ClientRects;
import echo.core.common.web.JQueryStringType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.*;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.adapters.IWebAdapter;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.test_abstraction.product.Configuration;
import echo.selenium.jQuery.IJavaScriptFlowExecutor;
import echo.selenium.jQuery.SeleniumScriptExecutor;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static echo.core.common.helpers.DateTimeExtensions.ApproximatelyEquals;
import static echo.core.common.helpers.StringUtils.Like;
import static echo.core.common.helpers.StringUtils.NormalizeSpacing;

public class SeleniumAdapter implements IWebAdapter, AutoCloseable {
    private WebDriver webDriver;
    private IJavaScriptFlowExecutor javaScriptExecutor;
    private ILog log;
    private boolean moveMouseToOrigin;

    public SeleniumAdapter() {
    }

    public SeleniumAdapter(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, ILog log, boolean moveMouseToOrigin) {
        this.javaScriptExecutor = javaScriptExecutor;
        this.webDriver = seleniumWebDriver;
        this.log = log;
        this.moveMouseToOrigin = moveMouseToOrigin;
    }

    public IAdapter Configure(Configuration configuration) {
        return SeleniumAdapterFactory.Create((SeleniumConfiguration) configuration);
    }

    public void close() {
        webDriver.quit();
        webDriver = null;
    }

    protected final WebDriver getWebDriver() {
        return webDriver;
    }

    private void setWebDriver(WebDriver value) {
        webDriver = value;
    }

    protected final IJavaScriptFlowExecutor getJavaScriptExecutor() {
        return javaScriptExecutor;
    }

    protected final ILog getLog() {
        return log;
    }

    /**
     * Adds a cookie.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param cookie The cookie to be added.
     */
    public final void AddCookie(UUID guid, IWebCookie cookie) {
        log.Trace(guid, "WebDriver.add_Cookie();");
        Cookie c = new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getExpiration());
        getWebDriver().manage().addCookie(c);
    }

    /**
     * Deletes all cookies.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void DeleteAllCookies(UUID guid) {
        log.Trace(guid, "WebDriver.delete_AllCookies();");
        getWebDriver().manage().deleteAllCookies();
    }

    /**
     * Deletes a cookie.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param name The name of the cookie to be deleted.
     */
    public final void DeleteCookie(UUID guid, String name) {
        log.Trace(guid, "WebDriver.delete_Cookie();");
        getWebDriver().manage().deleteCookieNamed(name);
    }

    /**
     * Gets the list of all cookies.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The list of cookies.
     */
    public final List<IWebCookie> GetAllCookies(UUID guid) {
        log.Trace(guid, "WebDriver.get_AllCookies();");

        List<IWebCookie> result = getWebDriver().manage().getCookies()
                .stream()
                .map(x -> new SeleniumCookie(x))
                .collect(Collectors.toList());

        log.Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets a cookie.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    public final IWebCookie GetCookie(UUID guid, String name) {
        log.Trace(guid, "WebDriver.get_Cookie();");

        Cookie cookie = getWebDriver().manage().getCookieNamed(name);
        IWebCookie result = null;
        if (cookie != null) {
            result = new SeleniumCookie(cookie);
        }

        getLog().Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public final void ModifyCookie(UUID guid, String name, String value) {
        log.Trace(guid, "WebDriver.modify_Cookie();");
        Cookie cookie = getWebDriver().manage().getCookieNamed(name);

        // Check if cookie actually exists.
        if (cookie == null) {
            throw new NoSuchCookieException(name);
        }

        // Delete old cookie, then add a new one with the new value.
        getWebDriver().manage().deleteCookieNamed(name);
        Cookie newCookie = new Cookie(cookie.getName(), value, cookie.getDomain(), cookie.getPath(), cookie.getExpiry());
        getWebDriver().manage().addCookie(newCookie);
    }

    /**
     * Gets the title of the current window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A string representation of the window's title.
     */
    public final String GetTitle(UUID guid) {
        log.Trace(guid, "WebDriver.get_Title();");
        String result = webDriver.getTitle();
        log.Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets the URL of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A URI object of the URL of the browser's window.
     */
    public final URL GetUrl(UUID guid) {
        log.Trace(guid, "WebDriver.get_Url();");
        String result = webDriver.getCurrentUrl();
        getLog().Trace(guid, String.format("Result: %1$s", result));
        try {
            return new URL(result);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * Gets the currently focused element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The currently focused element.
     */
    public final WebControl GetFocusedElement(UUID guid) {
        log.Trace(guid, "WebDriver.switch_To().active_Element()");
        org.openqa.selenium.WebElement result = webDriver.switchTo().activeElement();
        log.Trace(guid, String.format("Result: %1$s", result));
        return new SeleniumElement(result, log);
    }

    @Override
    public String GetElementTagName(UUID uuid, WebControl element) {
        return ((SeleniumElement) element).getUnderlyingWebElement().getTagName();
    }


    @Override
    public void ClearElement(UUID uuid, WebControl element) {
        ((SeleniumElement) element).Clear(uuid);
    }

    @Override
    public void ChooseSelectElementByValue(UUID uuid, WebControl element, String s) {
        throw new NotImplementedException("ChooseSelectElementByValue not implemented");
    }

    @Override
    public void ChooseSelectElementByText(UUID uuid, WebControl element, String s) {
        throw new NotImplementedException("ChooseSelectElementByText not implemented");
    }


    @Override
    public void ClickElement(UUID uuid, WebControl element) {
        ((SeleniumElement) element).getUnderlyingWebElement().click();
    }

    @Override
    public void SendKeysToElement(UUID uuid, WebControl element, String s) {
        ((SeleniumElement) element).getUnderlyingWebElement().sendKeys(s);
    }

    @Override
    public String GetElementAttribute(UUID uuid, WebControl element, String s) {
       return ((SeleniumElement) element).getUnderlyingWebElement().getAttribute(s);
    }

    @Override
    public void SwitchToMainWindow(UUID uuid) {
        throw new NotImplementedException("SwitchToMainWindow not implemented.");
    }

    /**
     * Gets the current browser window's handle.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A string representation of the current browser window's handle.
     */
    public final String GetCurrentWindowHandle(UUID guid) {
        log.Trace(guid, "WebDriver.get_CurrentWindowHandle();");
        String result = webDriver.getWindowHandle();
        log.Trace(guid, String.format("Result: %1$s", result));
        return result;
    }

    /**
     * Gets all the handles of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return A collection of all handles in the current browser window.
     */
    public final Collection<String> GetWindowHandles(UUID guid) {
        log.Trace(guid, "WebDriver.get_WindowHandles();");
        return webDriver.getWindowHandles();
    }

    /**
     * Navigates to the URL passed.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param url  The URL to navigate to.
     * @return A string representation of the handle in which is navigating to the URL.
     */
    public final String GoToUrl(UUID guid, URL url) {
        log.Trace(guid, String.format("WebDriver.Navigate().GoToUrl(\"%1$s\");", url));

        webDriver.navigate().to(url);

        return GetCurrentWindowHandle(guid);
    }

    /**
     * Scrolls to the top of the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void ScrollToTop(UUID guid) {
        ExecuteScript(guid, "window.scrollTo(0, 0);");
    }

    /**
     * Scrolls to the end of the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void ScrollToEnd(UUID guid) {
        ExecuteScript(guid, "window.scrollTo(0, document.body.scrollHeight || document.documentElement.scrollHeight);");
    }

    /**
     * Finds the first element that matches the corresponding IBy.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy Selector used to search with.
     * @return An IWebElementAdapter matching the findBy.
     */
    public WebControl FindElement(UUID guid, IBy findBy) {
        echo.core.common.web.selectors.By by =
                (echo.core.common.web.selectors.By)
                        ((findBy instanceof echo.core.common.web.selectors.By) ? findBy : null);

        if (by != null) {
            log.Trace(guid, String.format("WebDriver.FindElement(By.CssSelector(%1$s));", by));
            try {
                return new SeleniumElement(webDriver.findElement(org.openqa.selenium.By.cssSelector(findBy.toString())), log);
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementException(e);
            }
        }

        ByJQuery byJQuery = (ByJQuery) ((findBy instanceof ByJQuery) ? findBy : null);
        if (byJQuery != null) {
            return (WebControl) FindElements(guid, byJQuery).toArray()[0];
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Finds all elements with the matching IBy.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy Selector passed to search.
     * @return A ReadOnlyCollection of IWebElementAdapter.
     */
    public final Collection<WebControl> FindElements(UUID guid, IBy findBy) {
        echo.core.common.web.selectors.By by = (echo.core.common.web.selectors.By) ((findBy instanceof echo.core.common.web.selectors.By) ? findBy : null);
        if (by != null) {
            Collection<WebControl> collection;
            log.Trace(guid, String.format("WebDriver.FindElements(By.CssSelector(%1$s));", by));

            try {
                collection = webDriver.findElements(org.openqa.selenium.By.cssSelector(findBy.toString()))
                        .stream()
                        .map(e -> new SeleniumElement(e, log))
                        .collect(Collectors.toList());
            } catch (org.openqa.selenium.NoSuchElementException e) {
                throw new NoSuchElementsException(e);
            }

            if (collection.size() == 0) {
                throw new NoSuchElementsException();
            }

            return collection;
        }

        ByJQuery byJQuery = (ByJQuery) ((findBy instanceof ByJQuery) ? findBy : null);
        if (byJQuery != null) {
            try {
                return FindElements(guid, byJQuery);
            } catch (NoSuchElementException e2) {
                throw new NoSuchElementsException();
            }
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Returns a collection of all elements matching the JQuery selector.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The element(s) to find.
     * @return A ReadOnlyCollection of IWebElementAdapters.
     */
    private Collection<WebControl> FindElements(UUID guid, ByJQuery findBy) {
        String script = findBy.toString(JQueryStringType.ReturnElementArray);
        Object result = ExecuteScript(guid, script);

        if (result instanceof Collection<?> && ((Collection<?>) result).size() == 0) {
            throw new NoSuchElementException();
        }

        try {
            Collection<org.openqa.selenium.WebElement> elements =
                    (Collection<org.openqa.selenium.WebElement>) result;

            return elements.stream().map(e -> new SeleniumElement(e, log))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ScriptReturnValueNotHtmlElementException(result, script, e);
        }
    }

    /**
     * Finds an element within HTML code with the <select></select> tag.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy Selector object that we are using the find the <select></select> tag.
     * @return IWebSelectElementAdapter matching the findBy.
     */
    public final WebControl FindSelectElement(UUID guid, IBy findBy) {
        WebControl webElement = FindElement(guid, findBy);
        SeleniumElement elem = (SeleniumElement) ((webElement instanceof SeleniumElement) ? webElement : null);
        if (elem == null) {
            throw new UnsupportedSelectElementException(webElement.getClass());
        }

        return new SeleniumSelectElement(new Select(elem.getUnderlyingWebElement()), log);
    }

    /**
     * Switches to the default content of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void SwitchToDefaultContent(UUID guid) {
        log.Trace(guid, "WebDriver.SwitchTo().DefaultContent();");
        webDriver.switchTo().defaultContent();
    }

    /**
     * Switches to the specified frame in the HTML - <frame></frame>
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The selector of the element <frame></frame> to switch to.
     */
    public final void SwitchToFrame(UUID guid, IBy findBy) {
        WebControl webElement = FindElement(guid, findBy);
        SeleniumElement elem = (SeleniumElement) ((webElement instanceof SeleniumElement) ? webElement : null);
        if (elem == null) {
            throw new UnsupportedElementException(webElement.getClass());
        }

        log.Trace(guid, "WebDriver.SwitchTo().Frame(<IWebElement>);");

        webDriver.switchTo().frame(elem.getUnderlyingWebElement());
    }

    /**
     * Switches to the window with the corresponding windowTitle.
     *
     * @param guid        A globally unique identifier associated with this call.
     * @param windowTitle The title of the window we are searching for.
     * @return A string representation of the windowTitle we are searching for.
     */
    public final String SwitchToWindowByTitle(UUID guid, String windowTitle) {
        if (StringUtils.isEmpty(windowTitle) || windowTitle == null) {
            throw new IllegalArgumentException("windowTitle is null or an empty string");
        }
        for (String window : GetWindowHandles(guid)) {
            log.Trace(guid, String.format("WebDriver.SwitchTo().Window(%1$s);", window));

            webDriver.switchTo().window(window);

            if (GetTitle(guid).toLowerCase().contains(windowTitle.toLowerCase())) {
                return window;
            }
        }

        throw new NoSuchWindowException(windowTitle);
    }

    /**
     * Switches to the window with the corresponding 'handle' passed.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param handle The handle to switch to.
     */
    public final void SwitchToWindowByHandle(UUID guid, String handle) {
        log.Trace(guid, String.format("WebDriver.SwitchTo().Window(%1$s);", handle));
        webDriver.switchTo().window(handle);
    }

    /**
     * Switches to the window with the corresponding URL value.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The URL being searched for.
     * @return A string representation of the window's handle we switched to.
     */
    public final String SwitchToWindowByUrl(UUID guid, String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("value");
        }

        for (String window : GetWindowHandles(guid)) {
            log.Trace(guid, String.format("WebDriver.SwitchTo().Window(%1$s);", window));

            webDriver.switchTo().window(window);

            if (GetUrl(guid).toString().toLowerCase().contains(value.toLowerCase())) {
                return window;
            }
        }

        throw new NoSuchWindowException(value);
    }

    /**
     * Closes the current instance of the web browser, but does not Quit() the driver.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void Close(UUID guid) {
        SwitchToDefaultContent(guid);

        log.Trace(guid, "WebDriver.Close();");

        webDriver.close();
    }

    /**
     * Close()'s and terminates 'this' instance of the browser and the WebDriver.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void Quit(UUID guid) {
        log.Trace(guid, "WebDriver.Quit();");

        webDriver.quit();
    }

    /**
     * Verifies there is an alert raised on the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void VerifyAlertExists(UUID guid) {
        log.Trace(guid, "WebDriver.SwitchTo().Alert();");

        try {
            webDriver.switchTo().alert();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Verifies there is NOT an alert raised on the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void VerifyAlertNotExists(UUID guid) {
        log.Trace(guid, "WebDriver.SwitchTo().Alert();");

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
     * @param guid A globally unique identifier associated with this call.
     * @return The description of the alert dialog box.
     */
    public final String GetAlertText(UUID guid) {
        log.Trace(guid, "WebDriver.SwitchTo().Alert().get_Text();");

        return webDriver.switchTo().alert().getText();
    }

    /**
     * Sends the keysToSend to the alert in the current browser window.
     *
     * @param guid       A globally unique identifier associated with this call.
     * @param keysToSend Keys to send to the alert.
     */
    public final void SendKeysToAlert(UUID guid, String keysToSend) {
        log.Trace(guid, String.format("WebDriver.SwitchTo().Alert().SendKeys(%1$s);", keysToSend));
        webDriver.switchTo().alert().sendKeys(keysToSend);
    }

    /**
     * Accepts an alert on the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public void AcceptAlert(UUID guid) {
        try {
            log.Trace(guid, "WebDriver.SwitchTo().Alert();");
            webDriver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Dismisses an alert on the page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void DismissAlert(UUID guid) {
        try {
            log.Trace(guid, "WebDriver.SwitchTo().Alert().Dismiss();");
            webDriver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            throw new NoAlertException(e);
        }
    }

    /**
     * Focuses the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public void FocusWindow(UUID guid) {
        // Only implemented for InternetExplorer
    }

    /**
     * Executes the script(JavaScript) passed.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param script Script to execute.
     * @param args   Args to pass to JavaScriptExecutor.
     * @return An object returned from the script executed.
     */
    public final Object ExecuteScript(UUID guid, String script, Object... args) {
        try {
            return javaScriptExecutor.getExecutor()
                    .apply(new SeleniumScriptExecutor(webDriver, log), guid, script, Arrays.asList(args));

        } catch (RuntimeException e) {
            throw new ScriptExecutionException(script, e);
        }
    }

    /**
     * Accesses the history of the browser to execute the Back function.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void Back(UUID guid) {
        ExecuteScript(guid, "window.history.back();");
    }

    /**
     * Accesses the history of the browser to execute the Forward function.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void Forward(UUID guid) {
        ExecuteScript(guid, "window.history.forward();");
    }

    /**
     * Refreshes the current window in the active browser.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void Refresh(UUID guid) {
        log.Trace(guid, "WebDriver.Navigate().Refresh();");
        webDriver.navigate().refresh();
    }

    /**
     * Blurs the current element
     * @param guid A globally unique identifier associated with this call
     * @param element The element to be blurred
     */
    public final void Blur(UUID guid, WebControl element) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.BlurElement));");
        ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.BlurElement));
    }

    /**
     * Maximizes the browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public void Maximize(UUID guid) {
        try {
            log.Trace(guid, "WebDriver.Manage().Window.Maximize();");
            webDriver.manage().window().maximize();
        } catch (IllegalStateException e) {
            log.Trace(guid, "window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
            ExecuteScript(guid, "window.moveTo(0,0);window.resizeTo(screen.availWidth,screen.availHeight);");
        }
    }

    /**
     * Resizes the window to the desired Size dimensions.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param size Desired dimensions to resize the window to.
     */
    public void Resize(UUID guid, Size size) {
        log.Trace(guid, String.format("WebDriver.Manage().Window.set_Size(%1$s);", size));
        webDriver.manage().window().setSize(new Dimension(size.width, size.height));
    }

    /**
     * Finds the 'selector' on the page, and performs a Click() on the object.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The element on the page to click.
     */
    public void OpenFileDialog(UUID guid, IBy selector) {
        WebControl element = FindElement(guid, selector);
        Click(guid, element, moveMouseToOrigin);
    }

    /**
     * Uses keyboard native events to input file name and select it
     * from fileDialogBox
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The element on the page to click.
     */
    public void SelectFileDialog(UUID guid, IBy selector, String path) {
        try {
            SendKeysHelper.SendKeysToKeyboard(path);
            SendKeysHelper.SendEnterKey();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the 'selector' on the page, and performs a Click() on the object.
     * Then uses keyboard native events to input file name and select it.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The element on the page to click.
     */
    public void UploadFileDialog(UUID guid, IBy selector, String path) {
        WebControl element = FindElement(guid, selector);
        Click(guid, element, moveMouseToOrigin);
        try {
            SendKeysHelper.SendKeysToKeyboard(path);
            SendKeysHelper.SendEnterKey();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }



    private void Click(UUID guid, WebControl element, boolean moveMouseToOrigin) {
        ((SeleniumElement) element).Click(guid, moveMouseToOrigin);
    }

    @Override
    public void ScrollElementIntoView(UUID uuid, WebControl element) {
        ScrollElementIntoView(uuid, element.getSelector());
    }

    /**
     * Scrolls the element specified by the provided 'selector' into view.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector Element to scroll into view.
     */
    private final void ScrollElementIntoView(UUID guid, IBy selector) {
        ExecuteScript(guid, selector.ToJQuery().toString(JQueryStringType.ScrollElementIntoView));
        ExecuteScript(guid, "$(\"body\").scrollLeft(0);");
    }

    /**
     * Returns a screenshot of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return An Image object of the current browser.
     */
    public Image GetScreenshot(UUID guid) {
        TakesScreenshot driver = (TakesScreenshot) ((webDriver instanceof TakesScreenshot) ? webDriver : null);

        if (driver == null) {
            throw new IllegalStateException("Web IDriver does not support taking screenshot");
        }

        log.Trace(guid, "webDriver.getScreenshotAs(bytes);");

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
     * @param guid A globally unique identifier associated with this call.
     * @return A string representation of the source code of the page.
     */
    public final String GetPageSource(UUID guid) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

        log.Trace(guid, "WebDriver.get_PageSource();");

        return webDriver.getPageSource();
    }

    /**
     * Drag and drops the dropElement to the targetElement area.
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param dropElement   Element to be dragged.
     * @param targetElement Where the element will be dropped.
     */
    public final void DragAndDrop(UUID guid, IBy dropElement, IBy targetElement) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

        log.Trace(guid, "new Actions(IWebDriver).DragAndDrop(IWebElement, IWebElement);");

        WebElement drop = ((SeleniumElement) FindElement(guid, dropElement)).getUnderlyingWebElement();
        WebElement target = ((SeleniumElement) FindElement(guid, targetElement)).getUnderlyingWebElement();
        Actions builder = new Actions(webDriver);
        builder.clickAndHold(drop).perform();
        Sleep.Wait(250);
        builder.release(target);
        builder.perform();
        /*(new Actions(webDriver)).dragAndDrop(
                ((SeleniumElement) FindElement(guid, dropElement)).getUnderlyingWebElement(),
                ((SeleniumElement) FindElement(guid, targetElement)).getUnderlyingWebElement())
                .perform();*/
    }

    /**
     * Performs a RightClick on the element passed as an argument.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The element to perform the RightClick on.
     */
    public final void RightClick(UUID guid, IBy selector) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

        log.Trace(guid, "new Actions(IWebDriver).ContextClick(IWebElement);");

        (new Actions(webDriver)).contextClick(
                ((SeleniumElement) FindElement(guid, selector)).getUnderlyingWebElement())
                .perform();
    }

    /**
     * Performs a DoubleClick on the element passed as an argument.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The element to perform the DoubleClick on.
     */
    public final void DoubleClick(UUID guid, IBy selector) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

        log.Trace(guid, "new Actions(IWebDriver).DoubleClick(IWebElement);");

        (new Actions(webDriver)).doubleClick(
                ((SeleniumElement) FindElement(guid, selector)).getUnderlyingWebElement())
                .perform();
    }

    /**
     * Performs a LeftClick on the element passed as an argument.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to perform the Click on.
     */
    public final void Click(UUID guid, WebControl element) {
        // Check if wrapped (Might affect three browsers)
        String script = String.format(
                "var rects = %1$s[0].getClientRects(); return rects.length;", element.getSelector().ToJQuery());

        long rectsLength = (long) ExecuteScript(guid, script);

        if (rectsLength > 1) {
            script = String.format(
                    "var rects = %1$s[0].getClientRects(); var arr = [rects[0].left, rects[0].top, rects[0].right, rects[0].bottom]; return arr;",
                    element.getSelector().ToJQuery());

            Collection<Object> list = (Collection<Object>) ExecuteScript(guid, script);

            // (abstract/virtual)<--(depends on whether the class is to be made abstract or not) method to define the way the wrapping should be handled per browser
            WrappedClick(guid, element, new ArrayList<>(list));
        } else {
            Click(guid, element, moveMouseToOrigin);
        }
    }

    // Linked to selenium issue https://code.google.com/p/selenium/issues/detail?id=6702 and https://code.google.com/p/selenium/issues/detail?id=4618
    protected void WrappedClick(UUID guid, WebControl element, List<Object> list) {
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
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to click.
     * @param x       The x offset.
     * @param y       The y offset.
     */
    public final void ClickAtOffset(UUID guid, WebControl element, int x, int y) {
        SeleniumElement seleniumElement = (SeleniumElement) element;

        (new Actions(webDriver))
                .moveToElement(seleniumElement.getUnderlyingWebElement(), x, y)
                .click()
                .perform();
    }

    /**
     * Refreshes the current frame.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    public final void RefreshFrame(UUID guid) {
        if (webDriver == null) {
            throw new IllegalStateException("The driver is null.");
        }

        ExecuteScript(guid, "window.location = location.href;");
    }

    /**
     * Clicks and holds for the duration specified.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The web element to click.
     * @param duration Click for at least this long (in milliseconds).
     *                 <p>
     *                 <p>
     *                 Due to the way Windows handles event messages, thread context switches, thread sleep/awake, and Windows time ticks,
     *                 this command can only guarantee the click will be held for AT LEAST the time specified, not that the
     *                 click will be held for exactly the duration specified. It should have an accuracy approaching 15ms, but
     *                 depending on the system, it could be much worse. In some cases, the accuracy is much better, approaching
     *                 1ms. This can happen on Win7 and .Net 4 and higher, but having those do not guarantee it.
     *                 </p>
     *                 <p></p>
     *                 <p>
     *                 Another consideration to take into account is the specific browser you are using this command with.
     *                 Initial testing showed IE and Firefox to have much worse response time with this command in comparison to
     *                 Chrome.
     *                 </p>
     *                 <p></p>
     *                 <p>
     *                 If, for whatever reason, the command is interrupted and the click is not released, behavior can be quite weird.
     *                 The correct way to programatically recover from such a case would be to call Release() on the element that ClickAndHold
     *                 was called on, but any call that triggers a MouseUp event should suffice.
     *                 </p>
     */
    public void ClickAndHold(UUID guid, WebControl element, int duration) {
        SeleniumElement seleniumElement = (SeleniumElement) element;
        Actions action = new Actions(webDriver);

        // Click.
        action.clickAndHold(seleniumElement.getUnderlyingWebElement()).perform();

        // Hold the click.
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new UnableToHoldClickException();
        }

        // Release click.
        action.release(seleniumElement.getUnderlyingWebElement()).perform();
    }

    /**
     * Checks a checkbox.
     * @param guid A globally unique identifier associated with this call.
     * @param element The checkbox to be checked.
     */
    public void CheckElement (UUID guid, WebControl element) {
        if(!((SeleniumElement) element).getUnderlyingWebElement().isSelected()){
            Click(guid, element);
        }
    }

    /**
     * Unchecks a checkbox
     * @param guid A globally unique identifier associated with this call.
     * @param element The checkbox to be unchecked.
     */
    public void UnCheckElement(UUID guid, WebControl element) {
        if(((SeleniumElement) element).getUnderlyingWebElement().isSelected()){
            Click(guid, element);
        }
    }

    /**
     * Checks that an element is disabled.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element to check.
     */
    @Override
    public void IsElementDisabled(UUID guid, WebControl element) {
        if(((SeleniumElement) element).Enabled(guid)){
            throw new ElementIsEnabledException();
        }
    }

    /**
     * Checks that an element is enabled
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element to check.
     */
    public void IsElementEnabled(UUID guid, WebControl element) {
        boolean enabled = ((SeleniumElement) element).Enabled(guid);
        if (!enabled) {
            throw new ElementNotEnabledException();
        }
    }

    /**
     * If this method was called then the element exists.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element.
     */
    public void Exists(UUID guid, WebControl element) {
        //Logic done at command initialization
    }

    /**
     * If this method was reached then the element exists when it should not.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element.
     */
    public void NotExists(UUID guid, WebControl element) {
        //IF execution reachd here then element exists.
        throw new ElementExistsException();
    }

    private void hasOptions(UUID guid, SeleniumElement element, String [] options, WebSelectOption select) {
        try {
            for (String desiredOption : options) {
                if (select == WebSelectOption.Text)
                    element.FindElementByXPath(guid, echo.core.common.web.selectors.By.CssSelector(".//option[normalize-space(.) = " + Quotes.escape(desiredOption) + "]"));
                else
                    element.FindElement(guid, echo.core.common.web.selectors.By.CssSelector("option[value='".concat(desiredOption).concat("']")));
            }
        }
        catch (NoSuchElementException e) {throw new ElementDoesNotHaveOptionException(e.toString());}
    }

    private void DoesNotHaveOptions(UUID guid, SeleniumElement element, String [] options, WebSelectOption select) {
        for (String desiredOption:options) {
            boolean elementFound = true;
            try {
                if (select == WebSelectOption.Text)
                    element.FindElementByXPath(guid, echo.core.common.web.selectors.By.CssSelector(".//option[normalize-space(.) = " + Quotes.escape(desiredOption) + "]"));
                else
                    element.FindElement(guid, echo.core.common.web.selectors.By.CssSelector("option[value='".concat(desiredOption).concat("']")));
            }
            catch (NoSuchElementException e) {elementFound = false;}
            finally {if (elementFound) throw new ElementHasOptionException();}
        }
    }

    /**
     * Asserts that a select has all the options specified. Optionally can specify which option group the elements are part of.
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element which should contain the options.
     * @param options The options.
     * @param optgroup The optional option group.
     * @param select The method by which the options are identifed, either their value or their visible text.
     */
    public void ElementHasOptions (UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).GetTagName(guid).equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).GetTagName(guid), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).FindElement(guid, echo.core.common.web.selectors.By.CssSelector("optgroup[label='".concat(optgroup).concat("']")));
            hasOptions(guid, group, options, select);
        }
        else hasOptions(guid,(SeleniumElement) element, options, select);
     }

    /**
     * Asserts that a select element has none of the options specified. Optionally can specify which option group the elements are part of.
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element which should not contain the options.
     * @param options The options.
     * @param optgroup The optional option group.
     * @param select The method by which the options are identified, either their value or their visible text.
     */
    public void ElementDoesNotHaveOptions(UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).GetTagName(guid).equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).GetTagName(guid), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).FindElement(guid, echo.core.common.web.selectors.By.CssSelector("optgroup[label='".concat(optgroup).concat("']")));
            DoesNotHaveOptions(guid, group, options, select);
        }
        else DoesNotHaveOptions(guid,(SeleniumElement) element, options, select);
    }

    /**
     *
     * @param guid A globally unique identifier associated with this call.
     * @param element
     */
    @Override
    public void MouseOut(UUID guid, WebControl element) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.MouseOut));");
        ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.MouseOut));
    }

    /**
     *
     * @param guid
     * @param element
     */
    @Override
    public void MouseOver(UUID guid, WebControl element) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.MouseOver));");
        ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.MouseOver));
    }

    /**
     * Method asserts that the selected element's body tag will be changed into the provided String value
     * @param guid A globally unique identifier associated with this call.
     * @param element By The selector.
     * @param value Html to be inserted into body tag
     */
    @Override
    public void SetBodyValueByJavaScript(UUID guid, WebControl element, String value) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.SetBodyText));");
        ExecuteScript(guid, String.format(element.getSelector().ToJQuery().toString(JQueryStringType.SetBodyText), Quotes.escape(value)));

    }

    /**
     * Method asserts that the selected element's value tag will be changed into the provided String value
     * @param guid A globally unique identifier associated with this call.
     * @param element By The selector.
     * @param value Html to be inserted into a value tag
     */
    @Override
    public void SetValueByJavaScript(UUID guid, WebControl element, String value) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.SetValueText));");
        ExecuteScript(guid, String.format(element.getSelector().ToJQuery().toString(JQueryStringType.SetValueText), Quotes.escape(value)));
    }

    /**
     * Method asserts that the selected element's div tag will be changed into the provided String value
     * @param guid A globally unique identifier associated with this call.
     * @param element By The selector.
     * @param value Html to be inserted into div tag
     */
    @Override
    public void SetDivValueByJavaScript(UUID guid, WebControl element, String value) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.SetDivText));");
        ExecuteScript(guid, String.format(element.getSelector().ToJQuery().toString(JQueryStringType.SetDivText), Quotes.escape(value)));
    }

    /**
     * Method that clicks all elements that correspond with the given IBy.
     * @param guid A globally unique identifier associated with this call.
     * @param elementsBy The IBy that corresponds with all the elements to click.
     */
    public void ClickAllElements(UUID guid, IBy elementsBy) {
        Collection <WebControl> elements = FindElements(guid, elementsBy);
        for (WebControl element: elements) {
            ClickElement(guid, element);
        }
    }

    /**
     * Asserts that a select has all the specified elements in that order. Can optionally specify which optiongroup the elements are a part of.
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element to search.
     * @param options The options the element should have in the same order.
     * @param optgroup The option group the options should be a part of.
     * @param select The method by which the options are identifed, either their value, or their visible text.
     */
    public void ElementHasOptionsInOrder(UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {
        if (!((SeleniumElement) element).GetTagName(guid).equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).GetTagName(guid), "select");
        }
        if (optgroup != null) {
            SeleniumElement group = (SeleniumElement) ((SeleniumElement) element).FindElement(guid, echo.core.common.web.selectors.By.CssSelector("optgroup[label='" + optgroup + "']"));
            ElementHasOptionsInOrder(guid, group, options, select);
        }
        else ElementHasOptionsInOrder(guid, (SeleniumElement) element, options, select);
    }

    private void ElementHasOptionsInOrder(UUID guid, SeleniumElement element, String [] options, WebSelectOption select) {
        try {
            if (options.length  > 1) {
                if (select == WebSelectOption.Text) element = (SeleniumElement) element.FindElementByXPath(guid, echo.core.common.web.selectors.By.CssSelector(".//option[normalize-space(.) = " + Quotes.escape(options[0]) + "]"));

                for (int i = 1; i < options.length; i++) {
                    if (select == WebSelectOption.Value) {
                        element.FindElement(guid, echo.core.common.web.selectors.By.CssSelector("option[value='" + options[i-1] + "'] ~ option[value='" + options[i] + "']"));
                    } else
                        element = (SeleniumElement) element.FindElementByXPath(guid, echo.core.common.web.selectors.By.CssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[i]) + "]"));
                }
            }
            else{
                if (select == WebSelectOption.Value) {
                    element.FindElement(guid, echo.core.common.web.selectors.By.CssSelector("option[value='" + options[0] + "']"));
                } else element.FindElementByXPath(guid, echo.core.common.web.selectors.By.CssSelector(".//following-sibling::option[normalize-space(.) = " + Quotes.escape(options[0]) + "]"));
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new ElementDoesNotHaveOptionException(e.toString());
        }
    }

    /**
     * Asserts that a select element has a certain number of options. Can optionally specify an option group instead of the entire select.
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element.
     * @param optnumber The amount of options the element should have.
     * @param optgroup The optional option group to be searched.
     */
    public void HasNumberOfOptions(UUID guid, WebControl element, int optnumber, String optgroup) {
        if (!((SeleniumElement) element).GetTagName(guid).equals("select")) {
            throw new IncorrectElementTagException(((SeleniumElement) element).GetTagName(guid), "select");
        }
        if (optgroup != null) {
            element = (SeleniumElement) ((SeleniumElement) element).FindElement(guid, echo.core.common.web.selectors.By.CssSelector("optgroup[label='" + optgroup + "']"));
        }
        Collection <WebControl> options = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector("option"));
        if(options.size() != optnumber) {
            throw new ElementDoesNotHaveNumberOfOptionsException();
        }
    }

    /**
     * Asserts that a select has all of its options in order. The order can either be ascending or descending alphanumeric order by either the options value or their text.
     * @param guid A globally unique identifier associated with this call. Can optionally be passed an option group which will be searched instead of the entire select.
     * @param element The select element to be searched.
     * @param compare The method by which the options will be compared.
     * @param optGroup An optional option group which would be searched in isolation instad of all the options under select.
     */
    public void HasAllOptionsInOrder(UUID guid, WebControl element, CompareType compare, String optGroup) {
        if (optGroup != null) {
            element = ((SeleniumElement) element).FindElement(guid, echo.core.common.web.selectors.By.CssSelector("optgroup[label='" + optGroup + "']"));
        }
        Collection<WebControl> elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector("option"));
        Iterator <WebControl> elementsIterator = elements.iterator();
        SeleniumElement prevOption =(SeleniumElement) elementsIterator.next();
        SeleniumElement currOption;
        while (elementsIterator.hasNext()) {
            currOption = (SeleniumElement) elementsIterator.next();
            switch (compare) {
                case AscendingByText:
                    if (prevOption.GetText(guid).toLowerCase().compareTo(currOption.GetText(guid).toLowerCase()) < 0) {
                        throw new ElementsNotInOrderException();
                    }
                    break;
                case DescendingByText:
                    if (prevOption.GetText(guid).toLowerCase().compareTo(currOption.GetText(guid).toLowerCase()) > 0) {
                        throw new ElementsNotInOrderException();
                    }
                    break;
                case AscendingByValue:
                    if (prevOption.GetAttribute(guid, "value").toLowerCase().compareTo(currOption.GetAttribute(guid, "value")) < 0) {
                        throw new ElementsNotInOrderException();
                    }
                    break;
                case DescendingByValue:
                    if (prevOption.GetAttribute(guid, "value").toLowerCase().compareTo(currOption.GetAttribute(guid, "value")) > 0) {
                        throw new ElementsNotInOrderException();
                    }
                    break;
            }
            prevOption = currOption;
        }
    }

    /**
     * Checks to see if a selected element's checkbox is selected
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element.
     */
    public void Selected(UUID guid, WebControl element) {
        if (!((SeleniumElement) element).Selected(guid)) {
            throw new ElementNotSelectedException();
        }
    }

    /**
     * Checks to see if a selected element's checkbox is not selected
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element.
     */
    public void NotSelected(UUID guid, WebControl element) {
        if (((SeleniumElement) element).Selected(guid)) {
            throw new ElementIsSelectedException();
        }
    }

    /**
     * Checks to see if a selected element is visible
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element.
     */
    public void Visible(UUID guid, WebControl element) {
        if (!((SeleniumElement) element).Displayed(guid)) {
            throw new ElementNotVisibleException();
        }
    }

    /**
     * Checks to see if a selected element is hidden
     * @param guid A globally unique identifier associated with this call.
     * @param element The select element.
     */
    public void NotVisible(UUID guid, WebControl element) {
        if (((SeleniumElement) element).Displayed(guid)) {
            throw new ElementIsVisibleException();
        }
    }


    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web control whose children are to be searched.
     * @param messages The strings to be compared to.
     * @param selector The selectors that the children will be matched to.
     * @param option Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void Has(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.asList(messages).stream().map(x -> NormalizeSpacing(x)).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetText(guid))).collect(Collectors.toList());
            }
            else {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetAttribute(guid, attribute))).collect(Collectors.toList());
            }
        }
        else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector))
                    .stream().map(x -> NormalizeSpacing(((SeleniumElement) x).GetAttribute(guid, attribute))).collect(Collectors.toList());
        }
        for (String value : values) {
            if (!elements.contains(value)) {
                throw new ElementDoesNotHaveException(value);
            }
        }
    }

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * Comparisons are made ignoring whitespace and case.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web control whose children are to be searched.
     * @param messages The strings to be compared to.
     * @param selector The selectors that the children will be matched to.
     * @param option Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void HasLike(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection <String> values = Arrays.asList(messages).stream().map(x -> NormalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> echo.core.common.helpers.StringUtils.
                        NormalizeSpacing(((SeleniumElement) e).GetText(guid)).toLowerCase()).collect(Collectors.toList());
            }
            else {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> echo.core.common.helpers.StringUtils.
                        NormalizeSpacing(((SeleniumElement) e).GetAttribute(guid, attribute)).toLowerCase()).collect(Collectors.toList());
            }
        }
        else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector))
                    .stream().map(x -> echo.core.common.helpers.StringUtils.
                            NormalizeSpacing(((SeleniumElement) x).GetAttribute(guid, attribute).toLowerCase())).collect(Collectors.toList());
        }
        for (String value : values) {
            if (!elements.contains(value)) {
                throw new ElementDoesNotHaveException(value);
            }
        }
    }

    /**
     * Asserts that an elements children do not posses a text.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    public void DoesNotHave(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.asList(messages).stream().map(x -> NormalizeSpacing(x)).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetText(guid))).collect(Collectors.toList());
            }
            else {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetAttribute(guid, attribute))).collect(Collectors.toList());
            }
        }
        else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector))
                    .stream().map(x -> NormalizeSpacing(((SeleniumElement) x).GetAttribute(guid, attribute))).collect(Collectors.toList());
        }
        for (String value : values) {
            if (elements.contains(value)) {
                throw new ElementHasException(value);
            }
        }
    }

    /**
     * Asserts that an elements children do not posses a text. Comparisons made ignoring case and whitespace.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    public void DoesNotHaveLike(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.asList(messages).stream().map(x -> NormalizeSpacing(x).toLowerCase()).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetText(guid)).toLowerCase()).collect(Collectors.toList());
            }
            else {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetAttribute(guid, attribute)).toLowerCase()).collect(Collectors.toList());
            }
        }
        else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector))
                    .stream().map(x -> NormalizeSpacing(((SeleniumElement) x).GetAttribute(guid, attribute)).toLowerCase()).collect(Collectors.toList());
        }
        for (String value : values) {
            if (elements.contains(value)) {
                throw new ElementHasException(value);
            }
        }
    }


    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web control whose children are to be searched.
     * @param messages The strings to be compared to.
     * @param selector The selectors that the children will be matched to.
     * @param option Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    public void HasOnly(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        Collection<String> elements = null;
        Collection<String> values = Arrays.asList(messages).stream().map(x -> NormalizeSpacing(x)).collect(Collectors.toList());
        if (option == ComparisonOption.Text) {
            if (attribute.toUpperCase().equals("INNERHTML")) {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetText(guid))).collect(Collectors.toList());
            }
            else {
                elements = ((SeleniumElement) element).
                        FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector)).
                        stream().map(e -> NormalizeSpacing(((SeleniumElement) e).GetAttribute(guid, attribute))).collect(Collectors.toList());
            }
        }
        else if (option == ComparisonOption.Raw) {
            elements = ((SeleniumElement) element).FindElements(guid, echo.core.common.web.selectors.By.CssSelector(selector))
                    .stream().map(x -> NormalizeSpacing(((SeleniumElement) x).GetAttribute(guid, attribute))).collect(Collectors.toList());
        }
        for (String value : values) {
            if (!elements.contains(value)) {
                throw new ElementDoesNotHaveException(value);
            }
            elements.remove(value);
        }
        if (!elements.isEmpty()) {
            throw new ElementDoesNotOnlyHaveException (elements);
        }
    }

    /**
     * Asserts that an element's attribute is equal to a given value.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element.
     * @param value The value the attribute should be.
     * @param option Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    public void Is(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        if (option == ComparisonOption.Text && value.toUpperCase().equals("INNERHTML")) {
            if (!echo.core.common.helpers.StringUtils.Is(value, ((SeleniumElement) element).GetText(guid))) {
                throw new ValuesAreNotEqualException(value, ((SeleniumElement) element).GetText(guid), attribute);
            }
        } else {
            if (!echo.core.common.helpers.StringUtils.Is(value, ((SeleniumElement) element).GetAttribute(guid, attribute))) {
                throw new ValuesAreNotEqualException(value, ((SeleniumElement) element).GetAttribute(guid, attribute), attribute);
            }
        }
    }

    /**
     * Asserts that an element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element.
     * @param value The value the attribute should be.
     * @param option Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    public void IsLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        if (option == ComparisonOption.Text && value.toUpperCase().equals("INNERHTML")) {
            if (!Like(value, ((SeleniumElement) element).GetText(guid), false)) {
                throw new ValuesAreNotEqualException(value, ((SeleniumElement) element).GetText(guid), attribute);
            }
        } else {
            if (!Like(value, ((SeleniumElement) element).GetAttribute(guid, attribute), false)) {
                throw new ValuesAreNotEqualException(value, ((SeleniumElement) element).GetAttribute(guid, attribute), attribute);
            }
        }
    }

    /**
     * Asserts that an element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     * @param guid A globally unique identifier associated with this call.
     * @param element The web element.
     * @param value The value the attribute should be.
     * @param option Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    @Override
    public void IsNotLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        if (option == ComparisonOption.Text && value.toUpperCase().equals("INNERHTML")) {
            if (Like(value, ((SeleniumElement) element).GetText(guid), false)) {
                throw new ValuesAreAlikeException(value, ((SeleniumElement) element).GetText(guid));
            }
        } else {
            if (Like(value, ((SeleniumElement) element).GetAttribute(guid, attribute), false)) {
                throw new ValuesAreAlikeException(value, ((SeleniumElement) element).GetAttribute(guid, attribute));
            }
        }
    }

    @Override
    public void VerifyAlertText(UUID guid, String comparingText) {
        if(!echo.core.common.helpers.StringUtils.Is(GetAlertText(guid), comparingText)){
            throw new ValuesAreNotEqualException(GetAlertText(guid), comparingText);
        }
    }

    @Override
    public void VerifyAlertTextLike(UUID guid, String comparingText, boolean caseSensitive) {
        if(!echo.core.common.helpers.StringUtils.Like(GetAlertText(guid), comparingText, caseSensitive)){
            throw new ValuesAreNotAlikeException();
        }
    }

    @Override
    public void VerifyTitle(UUID guid, String comparingTitle) {
        if(!echo.core.common.helpers.StringUtils.Is(GetTitle(guid), comparingTitle)){
            throw new ValuesAreNotEqualException(GetTitle(guid), comparingTitle);
        }
    }

    @Override
    public void VerifyURL(UUID guid, URL comparingURL) {
        if(!GetUrl(guid).equals(comparingURL)){
            throw new ValuesAreNotEqualException(GetUrl(guid).toString(), comparingURL.toString());
        }
    }

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. Has a
     * Margin of error. The date must be in the ISO-8601 standard.
     * @param guid A globally unique identifier associated with this call.
     * @param element The element that posseses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expectedDate The expected date that the attribute should posses.
     * @param delta The margin of error that the date can be within. Cannot posses any weeks, months or years due to
     *              them having variable lengths.
     */
    @Override
    public void DatesApproximatelyEqual(UUID guid, WebControl element, String attributeName, DateTime expectedDate, Period delta) {
        String actualString = ((SeleniumElement) element).GetAttribute(guid, attributeName);
         try {
             DateTime actualDate = DateTime.parse(actualString);
             if (!ApproximatelyEquals(actualDate, expectedDate, delta)) {
                 throw new DatesNotApproximatelyEqualException(expectedDate, actualDate, delta);
             }
         } catch (IllegalArgumentException e) {
             throw new ElementAttributeNotADateException(attributeName, actualString);
         }
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     * @param guid A Globally unique identifier associated with this call.
     * @return Returns the BrowserType associated with this browser.
     */
    @Override
    public BrowserType GetBrowserType(UUID guid) {
        String name = (String) ExecuteScript(guid, "var browserType = \"\" + navigator.appName; return browserType;");
        String version = (String) ExecuteScript(guid, "var browserType = \"\" + navigator.appVersion; return browserType;");
        String userAgent = (String) ExecuteScript(guid,"var browserType = \"\" + navigator.userAgent; return browserType;");
        if (name.toLowerCase().contains("internet") && name.toLowerCase().contains("explorer")) {
            return BrowserType.InternetExplorer;
        }
        else if (version.toLowerCase().contains("chrome")) {
            return BrowserType.Chrome;
        }
        else if (userAgent.toLowerCase().contains("trident/7.0")) {
            return BrowserType.InternetExplorer;
        }
        else if (userAgent.toLowerCase().contains("firefox")) {
            return BrowserType.Firefox;
        } else {
            throw new BrowserTypeNotRecognizedException();
        }
    }

    /**
     * Gets the bounding rectangle for an element.
     * @param guid A Globally unique identifier associated with this call.
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    @Override
    public ClientRects GetClientRects(UUID guid, WebControl element) {
        log.Trace(guid, "ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.GetClientRects));");
        ArrayList rects = (ArrayList) ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.GetClientRects));
        int bottom = ((Number) rects.get(1)).intValue();
        int left = ((Number) rects.get(2)).intValue();
        int right = ((Number) rects.get(3)).intValue();
        int top = ((Number) rects.get(4)).intValue();
        return new ClientRects(top, bottom, left, right);
    }

    /**
     * Sends a non-alphanumeric keys to an element.
     * @param guid A globally unique identifier associated with this call.
     * @param element The element to recieve the keys.
     * @param key The key to be sent.
     */
    @Override
    public void PressKeyboardKey(UUID guid, WebControl element, KeyboardKey key) {
        ((SeleniumElement) element).getUnderlyingWebElement().sendKeys(Keys.getKeyFromUnicode(key.getUnicode()));
    }

    /**
     * Checks if a window does not exist by the title.
     * @param guid A globally unique identifier associated with this call.
     * @param windowTitle The title of the window to check for.
     * @return The title of the window.
     */
    @Override
    public String WindowDoesNotExistByTitle(UUID guid, String windowTitle) {
        if(windowTitle.isEmpty() || windowTitle == null){
            throw new IllegalArgumentException("window title is invalid");
        }
        try
        {
            SwitchToWindowByTitle(guid, windowTitle);
            throw new WindowExistsException(windowTitle);
        }catch (NoSuchWindowException e)
        {
            return windowTitle;
        }
    }

    /**
     * Checks if a window does not exist by the url.
     * @param guid A globally unique identifier associated with this call.
     * @param url The url of the window to check for.
     * @return The url of the window.
     */
    @Override
    public String WindowDoesNotExistByUrl(UUID guid, String url) {
        if(url.isEmpty() || url == null){
            throw new IllegalArgumentException("window title is invalid");
        }
        try
        {
            SwitchToWindowByUrl(guid, url);
            throw new WindowExistsException(url);
        }catch (NoSuchWindowException e)
        {
            return url;
        }
    }
}