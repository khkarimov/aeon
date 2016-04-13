package echo.core.test_abstraction.webuiobject;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.ICommandExecutionFacade;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_interaction.ElementType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * The publicly accessible elements of the browser.
 * <p>
 * This class cannot be inherited from, as this class uses explicit interface implementations.
 */
public final class BrowserObject implements IBrowserObject {
    private String currentWindowHandle;
    private String mainWindowHandle;
    private Iterable<Integer> browserPIDs;
    private ICommandExecutionFacade commandExecutionFacade;
    private WebCommandInitializer commandInitializer;
    private ParameterObject parameterObject;

    /**
     * Initializes a new instance of the <see cref="BrowserObject" /> class.
     *
     * @param automationInfo The automationInfo.
     */
    public BrowserObject(AutomationInfo automationInfo) {
        commandInitializer = new WebCommandInitializer();
        parameterObject = new ParameterObject(automationInfo, ElementType.Selenium);
        parameterObject.setLog(automationInfo.getLog());
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

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
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
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new CloseCommand(parameterObject.Log));
    }

    public void Quit() {
        // Clears browser storage if ensureCleanSession flag is set to true in app.config
        if (parameterObject.getAutomationInfo().IsCleanSession) {
            commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ClearBrowserStorageCommand(parameterObject.Log));
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new QuitCommand(parameterObject.Log));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToWindow(String windowTitle) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToWindow(windowTitle, false);
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToWindow(String windowTitle, boolean setMainWindow) {
        if (windowTitle == null) {
            throw new IllegalArgumentException("windowTitle");
        }

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToWindowByTitleCommand(parameterObject.Log, windowTitle));
        CurrentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);

        if (setMainWindow) {
            MainWindowHandle = CurrentWindowHandle;
        }

        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).Maximize();
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToMainWindow() {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToMainWindow(false);
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToMainWindow(boolean waitForAllPopupWindowsToClose) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToMainWindowCommand(parameterObject.Log, MainWindowHandle, waitForAllPopupWindowsToClose));

        CurrentWindowHandle = MainWindowHandle;
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToWindowByUrl(String url) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).SwitchToWindowByUrl(url, false);
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void SwitchToWindowByUrl(String value, boolean setMainWindow) {
        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new SwitchToWindowByUrlCommand(parameterObject.Log, value));
        CurrentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);

        if (setMainWindow) {
            MainWindowHandle = CurrentWindowHandle;
        }

        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).Maximize();
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoToUrl(String url) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).GoToUrl(new Uri(url));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoToUrl(Uri url) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).GoToUrl(url, false);
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoToUrl(String url, boolean setMainWindow) {
        ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).GoToUrl(new Uri(url), setMainWindow);
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoToUrl(Uri url, boolean setMainWindow) {
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        GoToUrlCommand command = new GoToUrlCommand(parameterObject.Log, url);

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), command);
        CurrentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);

        if (setMainWindow) {
            MainWindowHandle = CurrentWindowHandle;
        }
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void WindowDoesNotExistByTitle(String windowTitle) {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new WindowDoesNotExistByTitleCommand(parameterObject.Log, windowTitle, CurrentWindowHandle));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void WindowDoesNotExistByUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url");
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new WindowDoesNotExistByUrlCommand(parameterObject.Log, url, CurrentWindowHandle));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void Maximize() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new MaximizeCommand(parameterObject.Log));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void Resize(BrowserSize browserSize) {
        Resize(BrowserSizeMap.Map(browserSize));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void Resize(int browserWidth, int browserHeight) {
        Resize(new Size(browserWidth, browserHeight));
    }

    // executes a resize command.
    public final void Resize(Size size) {
        if (Size.OpEquality(size, Size.Empty)) {
            ((IBrowserObject) ((this instanceof IBrowserObject) ? this : null)).Maximize();
            return;
        }

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ResizeCommand(parameterObject.Log, size));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoBack() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GoBackCommand(parameterObject.Log));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void GoForward() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GoForwardCommand(parameterObject.Log));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void Refresh() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new RefreshCommand(parameterObject.Log));
    }

    // explicit interface to partially discourage public usage (i.e., hide IntelliSense) of this class (this class being only the implementation details), but encourage use of public interface IBrowserObject
    public final void AppendQueryString(String queryString) {
        if (queryString == null) {
            throw new IllegalArgumentException("queryString");
        }

        Object tempVar = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new AppendQueryStringCommand(parameterObject.Log, queryString));
        CurrentWindowHandle = (String) ((tempVar instanceof String) ? tempVar : null);
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
    public final void AddCookie(String name, String value, java.time.LocalDateTime expiry) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }

        if (value == null) {
            throw new IllegalArgumentException("value");
        }

        ICookieAdapter cookie = new SeleniumCookie(new Cookie(name, value, null, null, expiry));
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new AddCookieCommand(parameterObject.Log, cookie));
    }

    /**
     * Deletes all cookies.
     */
    public final void DeleteAllCookies() {
        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new DeleteAllCookiesCommand(parameterObject.Log));
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

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new DeleteCookieCommand(parameterObject.Log, name));
    }

    import java.util.*;

    /**
     * Gets all cookies.
     *
     * @return List of all cookies.
     */
    public final HashMap<String, HashMap<String, String>> GetAllCookies() {
        HashMap<> cookies = commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GetAllCookiesCommand(parameterObject.Log));
        HashMap<String, HashMap<String, String>> allCookies = new HashMap<String, HashMap<String, String>>();
        for (ArrayList<ICookieAdapter> cookie : (ArrayList<ICookieAdapter>) cookies) {
            if (!allCookies.containsKey(cookie.Domain)) {
                allCookies.put(cookie.Domain, new HashMap<String, String>() {
                    {
                        cookie.Name, cookie.Value
                    }
                });
            } else {
                allCookies.get(cookie.Domain).put(cookie.Name, cookie.Value);
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

        ICookieAdapter cookie = (ICookieAdapter) commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new GetCookieCommand(parameterObject.Log, name));

        return cookie == null ? "" : cookie.Value;
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

        commandExecutionFacade.Execute(parameterObject.getAutomationInfo(), new ModifyCookieCommand(parameterObject.Log, name, value));
    }
}
