package aeon.core.testabstraction.models;


import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.CloseCommand;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.helpers.URLUtil;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.IWebCookie;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class Browser {
    private AutomationInfo info;
    private String mainWindowHandle;

    public Browser(AutomationInfo info) {
        this.info = info;
    }

    public void AcceptAlert() {
        info.getCommandExecutionFacade().Execute(info, new AcceptAlertCommand());
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public void AddCookie(IWebCookie cookie) {
        info.getCommandExecutionFacade().Execute(info, new AddCookieCommand(cookie));
    }

    /**
     * Deletes all the cookies.
     */
    public void DeleteAllCookies() {
        info.getCommandExecutionFacade().Execute(info, new DeleteAllCookiesCommand());
    }

    /**
     * Deletes a cookie.
     *
     * @param cookie The name of the cookie to be deleted.
     */
    public void DeleteCookie(String cookie) {
        info.getCommandExecutionFacade().Execute(info, new DeleteCookieCommand(cookie));
    }

    /**
     * Dismisses an alert on a page.
     */
    public void DismissAlert() {
        info.getCommandExecutionFacade().Execute(info, new DismissAlertCommand());
    }

    /**
     * Gets the list of all the cookies.
     *
     * @return A collection of all the cookies in the browser.
     */
    public Collection<IWebCookie> GetAllCookies() {
        return (Collection<IWebCookie>) info.getCommandExecutionFacade().Execute(info, new GetAllCookiesCommand());
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return The BrowserType for the current browser.
     */
    public BrowserType GetBrowserType() {
        return (BrowserType) info.getCommandExecutionFacade().Execute(info, new GetBrowserTypeCommand());
    }

    /**
     * Get the body text of the alert that is raised on a page.
     *
     * @return The description of the alert dialog box.
     */
    public String GetAlertText() {
        return info.getCommandExecutionFacade().Execute(info, new GetAlertTextCommand()).toString();
    }

    /**
     * Gets a cookie.
     *
     * @param name The name of the cookie to be retrieved.
     * @return The specified cookie.
     */
    public IWebCookie GetCookie(String name) {
        return (IWebCookie) info.getCommandExecutionFacade().Execute(info, new GetCookieCommand(name));
    }

    /**
     * Access the history of the browser to execute the Back function.
     */
    public void GoBack() {
        info.getCommandExecutionFacade().Execute(info, new GoBackCommand());
    }

    /**
     * Access the history of the browser to execute the Forward function.
     */
    public void GoForward() {
        info.getCommandExecutionFacade().Execute(info, new GoForwardCommand());
    }

    /**
     * Navigates the browser the URL.
     *
     * @param url The URL the navigate to.
     */
    public void GoToUrl(String url) {
        mainWindowHandle = (String) info.getCommandExecutionFacade().Execute(info, new GoToUrlCommand(URLUtil.CreateURL(url)));
    }

    /**
     * Maximizes the browser window.
     */
    public void Maximize() {
        info.getCommandExecutionFacade().Execute(info, new MaximizeCommand());
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public void ModifyCookie(String name, String value) {
        info.getCommandExecutionFacade().Execute(info, new ModifyCookieCommand(name, value));
    }

    /**
     * Closes and terminates the active instances of the browser and WebDriver.
     */
    public void Quit() {
        info.getCommandExecutionFacade().Execute(info, new QuitCommand());
    }

    /**
     * Closes the currently focused browser window.     *
     */
    public void Close() { info.getCommandExecutionFacade().Execute(info, new CloseCommand());}


    /**
     * Refreshes the current window in the browser.
     */
    public void Refresh() {
        info.getCommandExecutionFacade().Execute(info, new RefreshCommand());
    }

    /**
     * Changes the browser window size.
     *
     * @param size The new size of the browser window based off the enumerable BrowserSize.
     */
    public void Resize(BrowserSize size) {
        info.getCommandExecutionFacade().Execute(info, new ResizeCommand(size));
    }

    /**
     * Scrolls to the top of the current page.
     */
    public void ScrollToTop() {
        info.getCommandExecutionFacade().Execute(info, new ScrollToTopCommand());
    }

    /**
     * Scrolls to the bottom of the current page.
     */
    public void ScrollToEnd() {
        info.getCommandExecutionFacade().Execute(info, new ScrollToEndCommand());
    }

    /**
     * Replicates sending keyboard strokes to the alert in the cirrent browser window.
     *
     * @param keys The values to send to the alert.
     */
    public void SendKeysToAlert(String keys) {
        info.getCommandExecutionFacade().Execute(info, new SendKeysToAlertCommand(keys));
    }

    /**
     * Switches to the main window of the current browser.
     *
     */
    public void SwitchToMainWindow() {
        SwitchToMainWindow(false);
    }

    /**
     * Switches to the main window of the current browser.
     *
     * @param waitForAllPopupsToClose Boolean switch to wait for all popup windows to be closed before switching,
     */
    public void SwitchToMainWindow(Boolean waitForAllPopupsToClose) {
        info.getCommandExecutionFacade().Execute(info, new SwitchToMainWindowCommand(mainWindowHandle, waitForAllPopupsToClose));
    }

    /**
     * Switches to the window with the corresponding title.
     *
     * @param title The title of the window to switch to
     */
    public void SwitchToWindowByTitle(String title) {
        info.getCommandExecutionFacade().Execute(info, new SwitchToWindowByTitleCommand(title));
    }

    /**
     * Switches to the window with the corresponding URL.
     *
     * @param url The URL of the window to switch to.
     */
    public void SwitchToWindowByUrl(String url) {
        info.getCommandExecutionFacade().Execute(info, new SwitchToWindowByUrlCommand(url));
    }

    /**
     * Asserts that an alert is not present on the current page.
     */
    public void VerifyAlertExists() {
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertExistsCommand());
    }

    /**
     * Asserts that an alert is present on the current page.
     */
    public void VerifyAlertNotExists() {
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertNotExistsCommand());
    }

    /**
     * Asserts the description of the alert on the current page.
     *
     * @param comparingText The expected description on the alert.
     */
    public void VerifyAlertText(String comparingText) {
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertTextCommand(comparingText));
    }

    /**
     * @param comparingText
     * @param caseSensitive
     */
    public void VerifyAlertTextLike(String comparingText, boolean caseSensitive) {
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertTextLikeCommand(comparingText, caseSensitive));
    }

    public void VerifyTitle(String comparingTitle) {
        info.getCommandExecutionFacade().Execute(info, new VerifyTitleCommand(comparingTitle));
    }

    public void VerifyURL(String comparingURL) {
        info.getCommandExecutionFacade().Execute(info, new VerifyUrlCommand(comparingURL));
    }

    public void VerifyWindowDoesNotExistByTitle(String windowTitle) {
        info.getCommandExecutionFacade().Execute(info, new WindowDoesNotExistByTitleCommand(windowTitle));
    }

    public void VerifyWindowDoesNotExistByUrl(String url) {
        info.getCommandExecutionFacade().Execute(info, new WindowDoesNotExistByUrlCommand(url));
    }

    public void ClickAllElementsCommand(IBy selector) {
        info.getCommandExecutionFacade().Execute(
                info,
                new ClickAllElementsCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
