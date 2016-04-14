package echo.selenium;

import com.sun.glass.ui.Size;
import echo.core.common.JQueryStringType;
import echo.core.common.exceptions.*;
import echo.core.common.exceptions.NoSuchElementException;
import echo.core.common.exceptions.NoSuchWindowException;
import echo.core.common.logging.ILog;
import echo.core.common.webobjects.By;
import echo.core.common.webobjects.ByJQuery;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.Configuration;
import echo.core.framework_abstraction.IAdapter;
import echo.core.framework_abstraction.ICookie;
import echo.core.framework_abstraction.IElement;
import echo.selenium.jQuery.IJavaScriptFlowExecutor;
import echo.selenium.jQuery.SeleniumScriptExecutor;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumAdapter implements IAdapter, AutoCloseable {
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
        return SeleniumAdapterFactory.Create((SeleniumConfiguration)configuration);
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
    public final void AddCookie(UUID guid, ICookie cookie) {
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
    public final List<ICookie> GetAllCookies(UUID guid) {
        log.Trace(guid, "WebDriver.get_AllCookies();");

        List<ICookie> result = getWebDriver().manage().getCookies()
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
    public final ICookie GetCookie(UUID guid, String name) {
        log.Trace(guid, "WebDriver.get_Cookie();");

        Cookie cookie = getWebDriver().manage().getCookieNamed(name);
        ICookie result = null;
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
    public final IElement GetFocusedElement(UUID guid) {
        log.Trace(guid, "WebDriver.switch_To().active_Element()");
        org.openqa.selenium.WebElement result = webDriver.switchTo().activeElement();
        log.Trace(guid, String.format("Result: %1$s", result));
        return new SeleniumElement(result, log);
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
    public IElement FindElement(UUID guid, IBy findBy) {
        By by = (By) ((findBy instanceof By) ? findBy : null);
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
            return (IElement) FindElements(guid, byJQuery).toArray()[0];
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
    public final Collection<IElement> FindElements(UUID guid, IBy findBy) {
        By by = (By) ((findBy instanceof By) ? findBy : null);
        if (by != null) {
            Collection<IElement> collection;
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
    private Collection<IElement> FindElements(UUID guid, ByJQuery findBy) {
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
    public final IElement FindSelectElement(UUID guid, IBy findBy) {
        IElement webElement = FindElement(guid, findBy);
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
        IElement webElement = FindElement(guid, findBy);
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
        if (StringUtils.isEmpty(windowTitle)) {
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
    public final Object ExecuteScript(UUID guid, String script, Object ... args) {
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
        IElement element = FindElement(guid, selector);
        element.Click(guid, moveMouseToOrigin);
    }

    /**
     * Scrolls the element specified by the provided 'selector' into view.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector Element to scroll into view.
     */
    public final void ScrollElementIntoView(UUID guid, IBy selector) {
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

        (new Actions(webDriver)).dragAndDrop(
                ((SeleniumElement) FindElement(guid, dropElement)).getUnderlyingWebElement(),
                ((SeleniumElement) FindElement(guid, targetElement)).getUnderlyingWebElement())
                .perform();
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
    public final void Click(UUID guid, IElement element) {
        // Check if wrapped (Might affect three browsers)
        String script = String.format(
                "var rects = %1$s[0].getClientRects(); return rects.length;", element.getSelector().ToJQuery());

        int rectsLength = (int) ExecuteScript(guid, script);

        if (rectsLength > 1) {
            script = String.format(
                    "var rects = %1$s[0].getClientRects(); var arr = [rects[0].left, rects[0].top, rects[0].right, rects[0].bottom]; return arr;",
                    element.getSelector().ToJQuery());

            Collection<Object> list = (Collection<Object>) ExecuteScript(guid, script);

            // (abstract/virtual)<--(depends on whether the class is to be made abstract or not) method to define the way the wrapping should be handled per browser
            WrappedClick(guid, element, new ArrayList<>(list));
        } else {
            element.Click(guid, moveMouseToOrigin);
        }
    }

    // Linked to selenium issue https://code.google.com/p/selenium/issues/detail?id=6702 and https://code.google.com/p/selenium/issues/detail?id=4618
    protected void WrappedClick(UUID guid, IElement element, List<Object> list) {
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
    public final void ClickAtOffset(UUID guid, IElement element, int x, int y) {
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
    public void ClickAndHold(UUID guid, IElement element, int duration) {
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
}