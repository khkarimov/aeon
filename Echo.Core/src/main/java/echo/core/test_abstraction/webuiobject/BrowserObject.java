package echo.core.test_abstraction.webuiobject;

import com.sun.glass.ui.Size;
import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.ICommandExecutionFacade;
import echo.core.command_execution.commands.*;
import echo.core.common.BrowserSize;
import echo.core.common.BrowserSizeMap;
import echo.core.common.Resources;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.webdriver.ICookieAdapter;
import echo.core.framework_interaction.ElementType;
import echo.core.framework_interaction.selenium.SeleniumCookie;
import org.openqa.selenium.Cookie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The publicly accessible elements of the browser.
 * <p>
 * This class cannot be inherited from, as this class uses explicit interface implementations.
 */
public final class BrowserObject implements IBrowserObject {
    private String currentWindowHandle;
    private String mainWindowHandle;
    private ICommandExecutionFacade commandExecutionFacade;
    private ParameterObject parameterObject;

    /**
     * Initializes a new instance of the <see cref="BrowserObject" /> class.
     *
     * @param automationInfo The automationInfo.
     */
    public BrowserObject(AutomationInfo automationInfo) {
        parameterObject = new ParameterObject(automationInfo, ElementType.Selenium);
        parameterObject.setLog(automationInfo.getLog());
        commandExecutionFacade = automationInfo.getCommandExecutionFacade();
    }

    public String getCurrentWindowHandle() {
        return currentWindowHandle;
    }

    public void setCurrentWindowHandle(String value) {
        currentWindowHandle = value;
    }

    public String getMainWindowHandle() {
        return mainWindowHandle;
    }

    public void setMainWindowHandle(String value) {
        mainWindowHandle = value;
    }

    // TODO: ensure SendKeys works in a grid instance
    public void SendKeys(String keysToSend) {
        if (keysToSend == null) {
            throw new IllegalArgumentException("keysToSend");
        }

        parameterObject.getLog()
                .Info(UUID.randomUUID(),
                        String.format(Resources.getString("CommandInstantiated_Info"),
                                String.format(Resources.getString("BrwObj_SendKeys_Info"), keysToSend)));

        // TODO: JAVA_CONVERSION
        //SendKeys.SendWait(keysToSend);
    }

    public void ScrollToTop() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(),
                new ScrollToTopCommand(parameterObject.getLog()));
    }

    public void ScrollToEnd() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ScrollToEndCommand(parameterObject.getLog()));
    }

    /**
     * <p>Verifies the current title.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyTitle("Title");</p>
     *
     * @param title The title to verify.
     */
    public void VerifyTitle(String title) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new VerifyTitleCommand(parameterObject.getLog(), title));
    }

    /**
     * <p>Verifies the current URL.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyUrl("URL");</p>
     *
     * @param url The url to verify.
     */
    public void VerifyUrl(String url) {
        try {
            VerifyUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Verifies the current URL.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyUrl(Uri url);</p>
     *
     * @param url The url to verify.
     */
    public void VerifyUrl(URL url) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new VerifyUrlCommand(parameterObject.getLog(), url));
    }

    public void SendKeysAndEnter(String keysToSend) {
        SendKeys(String.format("%1$s{ENTER}", keysToSend));
    }

    public void Close() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new CloseCommand(parameterObject.getLog()));
    }

    public void Quit() {
        if (parameterObject.getAutomationInfo().getParameters().getBoolean("ensureCleanSession")) {
            commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ClearBrowserStorageCommand(parameterObject.getLog()));
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new QuitCommand(parameterObject.getLog()));
    }

    public final void SwitchToWindow(String windowTitle) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToWindow(windowTitle, false);
    }

    public final void SwitchToWindow(String windowTitle, boolean setMainWindow) {
        if (windowTitle == null) {
            throw new IllegalArgumentException("windowTitle");
        }

        Object output = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToWindowByTitleCommand(parameterObject.getLog(), windowTitle));
        String currentWindowHandle = (String) ((output instanceof String) ? output : null);

        if (setMainWindow) {
            mainWindowHandle = currentWindowHandle;
        }

        Maximize();
    }

    public final void SwitchToMainWindow() {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToMainWindow(false);
    }

    public final void SwitchToMainWindow(boolean waitForAllPopupWindowsToClose) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToMainWindowCommand(parameterObject.getLog(), mainWindowHandle, waitForAllPopupWindowsToClose));

        currentWindowHandle = mainWindowHandle;
    }

    public final void SwitchToWindowByUrl(String url) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToWindowByUrl(url, false);
    }

    public final void SwitchToWindowByUrl(String value, boolean setMainWindow) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToWindowByUrlCommand(parameterObject.getLog(), value));
        String currentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);

        if (setMainWindow) {
            mainWindowHandle = currentWindowHandle;
        }

        Maximize();
    }

    public final void GoToUrl(String url) {
        try {
            GoToUrl(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public final void GoToUrl(URL url) {
        GoToUrl(url, false);
    }

    public final void GoToUrl(String url, boolean setMainWindow) {
        try {
            GoToUrl(new URL(url), setMainWindow);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public final void GoToUrl(URL url, boolean setMainWindow) {
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        GoToUrlCommand command = new GoToUrlCommand(parameterObject.getLog(), url);

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), command);
        String currentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);

        if (setMainWindow) {
            mainWindowHandle = currentWindowHandle;
        }
    }

    public final void WindowDoesNotExistByTitle(String windowTitle) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new WindowDoesNotExistByTitleCommand(parameterObject.getLog(), windowTitle, currentWindowHandle));
    }

    public final void WindowDoesNotExistByUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new WindowDoesNotExistByUrlCommand(parameterObject.getLog(), url, currentWindowHandle));
    }

    public final void Maximize() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new MaximizeCommand(parameterObject.getLog()));
    }

    public final void Resize(BrowserSize browserSize) {
        Resize(BrowserSizeMap.Map(browserSize));
    }

    public final void Resize(int browserWidth, int browserHeight) {
        Resize(new Size(browserWidth, browserHeight));
    }

    // executes a resize command.
    public final void Resize(Size size) {
        if (size.width == 0 && size.height == 0) {
            Maximize();
            return;
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ResizeCommand(parameterObject.getLog(), size));
    }

    public final void GoBack() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GoBackCommand(parameterObject.getLog()));
    }

    public final void GoForward() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GoForwardCommand(parameterObject.getLog()));
    }

    public final void Refresh() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new RefreshCommand(parameterObject.getLog()));
    }

    public final void AppendQueryString(String queryString) {
        if (queryString == null) {
            throw new IllegalArgumentException("queryString");
        }

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new AppendQueryStringCommand(parameterObject.getLog(), queryString));
        currentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);
    }

    /**
     * Adds a cookie.
     *
     * @param name  The name of the cookie to be added.
     * @param value The value of the cookie to be added.
     */
    public final void AddCookie(String name, String value) {
        AddCookie(name, value, null);
    }

    /**
     * Adds a cookie.
     *
     * @param name   The name of the cookie to be added.
     * @param value  The value of the cookie.
     * @param expiry The expiry date of the cookie.
     */
    public final void AddCookie(String name, String value, Date expiry) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        ICookieAdapter cookie = new SeleniumCookie(new Cookie(name, value, null, null, expiry));
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new AddCookieCommand(parameterObject.getLog(), cookie));
    }

    /**
     * Deletes all cookies.
     */
    public final void DeleteAllCookies() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new DeleteAllCookiesCommand(parameterObject.getLog()));
    }

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be deleted.
     */
    public final void DeleteCookie(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new DeleteCookieCommand(parameterObject.getLog(), name));
    }

    /**
     * Gets all cookies.
     *
     * @return List of all cookies.
     */
    public final HashMap<String, HashMap<String, String>> GetAllCookies() {
        List<ICookieAdapter> cookies =
                (List<ICookieAdapter>)
                        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(),
                                new GetAllCookiesCommand(parameterObject.getLog()));

        HashMap<String, HashMap<String, String>> allCookies = new HashMap<String, HashMap<String, String>>();
        for (ICookieAdapter cookie : cookies) {
            if (!allCookies.containsKey(cookie.getDomain())) {
                allCookies.put(cookie.getDomain(), new HashMap<String, String>() {
                    {
                        put(cookie.getName(), cookie.getValue());
                    }
                });
            } else {
                allCookies.get(cookie.getDomain()).put(cookie.getName(), cookie.getValue());
            }
        }

        return allCookies;
    }

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    public final String GetCookie(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        ICookieAdapter cookie = (ICookieAdapter) commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GetCookieCommand(parameterObject.getLog(), name));

        return cookie == null ? "" : cookie.getValue();
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public final void ModifyCookie(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ModifyCookieCommand(parameterObject.getLog(), name, value));
    }
}
