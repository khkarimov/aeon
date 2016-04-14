package echo.core.test_abstraction.webuiobject;

import echo.core.common.BrowserSize;

import java.net.URL;
import java.util.*;

/**
 * The interface for <see cref="BrowserObject"/>.
 */
public interface IBrowserObject {
    /**
     * <p>Closes the currently focused browser window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Close();</p>
     */
    void Close();

    /**
     * <p>Closes the specific browser windows tied to this context.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Quit();</p>
     */
    void Quit();

    /**
     * <p>Verifies the current Title.</p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyTitle(title);</p>
     *
     * @param title The title to verify.
     */
    void VerifyTitle(String title);

    /**
     * <p>Verifies the current URL.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyUrl(Uri url);</p>
     *
     * @param url The url to verify.
     */
    void VerifyUrl(URL url);

    /**
     * <p>Verifies the current URL.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.VerifyUrl("URL");</p>
     *
     * @param url The url to verify.
     */
    void VerifyUrl(String url);

    /**
     * <p>Sends keys to the current Browser.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SendKeys("keysToSend");</p>
     *
     * @param keysToSend The keystrokes to send.
     */
    void SendKeys(String keysToSend);

    /**
     * <p>Sends keys to the current Browser.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SendKeysAndEnter("keysToSend");</p>
     *
     * @param keysToSend The keystrokes to send.
     */
    void SendKeysAndEnter(String keysToSend);

    /**
     * <p>Switches focus to a specified window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToWindow("windowTitle");</p>
     *
     * @param windowTitle The window whose title to which to switch focus.
     */
    void SwitchToWindow(String windowTitle);

    /**
     * <p>Switches focus to a specified window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToWindow("windowTitle", setMainWindowBoolean);</p>
     *
     * @param windowTitle   The window whose title to which to switch focus.
     * @param setMainWindow Indicates to set the main window for future calls to <see cref="SwitchToMainWindow()"/>.
     */
    void SwitchToWindow(String windowTitle, boolean setMainWindow);

    /**
     * <p>Switches focus to the original window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToMainWindow();</p>
     */
    void SwitchToMainWindow();

    /**
     * <p>Switches focus to the original window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToMainWindow(waitForAllPopupWindowsToClose);</p>
     *
     * @param waitForAllPopupWindowsToClose <code>true</code> indicates to wait for every popup window to close; <code>false</code> indicates otherwise.
     */
    void SwitchToMainWindow(boolean waitForAllPopupWindowsToClose);

    /**
     * <p>Switches focus to a specified window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToWindowByUrl("url");</p>
     *
     * @param value The URL to which to switch focus.
     */
    void SwitchToWindowByUrl(String value);

    /**
     * <p>Switches focus to a specified window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.SwitchToWindowByUrl("url", setMainWindowBoolean);</p>
     *
     * @param value         The URL to which to switch focus.
     * @param setMainWindow Indicates to set the main window for future calls to <see cref="SwitchToMainWindow()"/>.
     */
    void SwitchToWindowByUrl(String value, boolean setMainWindow);

    /**
     * <p>Navigate the currently focused browser to the URL provided.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoToUrl("url String")</p>
     *
     * @param url The URL to which to navigate.
     */
    void GoToUrl(String url);

    /**
     * <p>Navigate the currently focused browser to the URL provided.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoToUrl(<see cref="Uri"/>)</p>
     *
     * @param url The URL to which to navigate.
     */
    void GoToUrl(URL url);

    /**
     * <p>Scrolls to the top of the page.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.ScrollToTop();</p>
     */
    void ScrollToTop();

    /**
     * <p>Scrolls to the end of the page.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.ScrollToEnd();</p>
     */
    void ScrollToEnd();

    /**
     * <p>Navigate the currently focused browser to the URL provided.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoToUrl("url String", setMainWindowBoolean)</p>
     *
     * @param url           The URL to which to navigate.
     * @param setMainWindow Indicates to set the main window for future calls to <see cref="SwitchToMainWindow()"/>.
     */
    void GoToUrl(String url, boolean setMainWindow);

    /**
     * <p>Navigate the currently focused browser to the URL provided.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoToUrl(<see cref="Uri"/>, setMainWindowBoolean)</p>
     *
     * @param url           The URL to which to navigate.
     * @param setMainWindow Indicates to set the main window for future calls to <see cref="SwitchToMainWindow()"/>.
     */
    void GoToUrl(URL url, boolean setMainWindow);

    /**
     * <p>Checks for the absense of a window by title.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.WindowDoesNotExistByTitle("windowTitle");</p>
     *
     * @param windowTitle The title of the window.
     */
    void WindowDoesNotExistByTitle(String windowTitle);

    /**
     * <p>Checks for the absense of a window by URL.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.WindowDoesNotExistByTitle("windowURL");</p>
     *
     * @param url The URL of the window.
     */
    void WindowDoesNotExistByUrl(String url);

    /**
     * <p>Maximizes the currently focused browser window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Maximize();</p>
     */
    void Maximize();

    /**
     * <p>Resizes the currently focused browser window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Resize(BrowserSize.Maximized);</p>
     *
     * @param browserSize The new browser size.
     */
    void Resize(BrowserSize browserSize);

    /**
     * <p>Resizes the currently focused browser window.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Resize(800, 600);</p>
     *
     * @param browserWidth  The new browser width.
     * @param browserHeight The new browser height.
     */
    void Resize(int browserWidth, int browserHeight);

    /**
     * <p>Move back a single entry in the browser's history.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoBack();</p>
     */
    void GoBack();

    /**
     * <p>Move forward a single entry in the browser's history.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GoForward();</p>
     * <p>
     * Does nothing if we are on the latest page viewed.
     */
    void GoForward();

    /**
     * <p>Refreshes the current page.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.Refresh();</p>
     */
    void Refresh();

    /**
     * <p>Appends a query string to the current URL in the browser.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.AppendQueryString("QueryString");</p>
     *
     * @param queryString The query string to append(do not include the ?).
     */
    void AppendQueryString(String queryString);

    /**
     * <p>Gets all cookies.</p>
     * <p></p>
     * <p>Usage:</p>
     * <p>      Context.Browser.GetAllCookies();</p>
     *
     * @return List of all cookies.
     */
    HashMap<String, HashMap<String, String>> GetAllCookies();

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return Value of the specified cookie.
     */
    String GetCookie(String name);

    /**
     * Adds a cookie.
     *
     * @param name  Name of the cookie.
     * @param value Value of the cookie.
     */
    void AddCookie(String name, String value);

    /**
     * Adds a cookie.
     *
     * @param name   Name of the cookie.
     * @param value  Value of the cookie.
     * @param expiry Expiry date of the cookie.
     */
    void AddCookie(String name, String value, Date expiry);

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be deleted.
     */
    void DeleteCookie(String name);

    /**
     * Deletes all cookies.
     */
    void DeleteAllCookies();

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The new value of the cookie.
     */
    void ModifyCookie(String name, String value);
}
