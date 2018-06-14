package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.CloseCommand;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.helpers.URLUtil;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Browser class.
 */
public class Browser {

    private AutomationInfo info;
    private String mainWindowHandle;

    /**
     * The constructor for the Browser given an AutomationInfo object.
     *
     * @param info sets the info of the newly made Browser.
     */
    public Browser(AutomationInfo info) {
        this.info = info;
    }

    /**
     * Function to accept an incoming alert.
     */
    public void acceptAlert() {
        info.getCommandExecutionFacade().execute(info, new AcceptAlertCommand());
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public void addCookie(IWebCookie cookie) {
        info.getCommandExecutionFacade().execute(info, new AddCookieCommand(cookie));
    }

    /**
     * Deletes all the cookies.
     */
    public void deleteAllCookies() {
        info.getCommandExecutionFacade().execute(info, new DeleteAllCookiesCommand());
    }

    /**
     * Deletes a cookie.
     *
     * @param cookie The name of the cookie to be deleted.
     */
    public void deleteCookie(String cookie) {
        info.getCommandExecutionFacade().execute(info, new DeleteCookieCommand(cookie));
    }

    /**
     * Dismisses an alert on a page.
     */
    public void dismissAlert() {
        info.getCommandExecutionFacade().execute(info, new DismissAlertCommand());
    }

    /**
     * Gets the list of all the cookies.
     *
     * @return A collection of all the cookies in the browser.
     */
    public Collection<IWebCookie> getAllCookies() {
        return (Collection<IWebCookie>) info.getCommandExecutionFacade().execute(info, new GetAllCookiesCommand());
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return The BrowserType for the current browser.
     */
    public BrowserType getBrowserType() {
        return ((WebConfiguration) info.getConfiguration()).getBrowserType();
    }

    /**
     * Get the body text of the alert that is raised on a page.
     *
     * @return The description of the alert dialog box.
     */
    public String getAlertText() {
        return (String) info.getCommandExecutionFacade().execute(info, new GetAlertTextCommand());
    }

    /**
     * Gets a cookie.
     *
     * @param name The name of the cookie to be retrieved.
     * @return The specified cookie.
     */
    public IWebCookie getCookie(String name) {
        return (IWebCookie) info.getCommandExecutionFacade().execute(info, new GetCookieCommand(name));
    }

    /**
     * Access the history of the browser to execute the Back function.
     */
    public void goBack() {
        info.getCommandExecutionFacade().execute(info, new GoBackCommand());
    }

    /**
     * Access the history of the browser to execute the Forward function.
     */
    public void goForward() {
        info.getCommandExecutionFacade().execute(info, new GoForwardCommand());
    }

    /**
     * Navigates the browser the URL.
     *
     * @param url The URL the navigate to.
     */
    public void goToUrl(String url) {
        mainWindowHandle = (String) info.getCommandExecutionFacade().execute(info, new GoToUrlCommand(URLUtil.createURL(url)));
    }

    /**
     * Maximizes the browser window.
     */
    public void maximize() {
        info.getCommandExecutionFacade().execute(info, new MaximizeCommand());
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public void modifyCookie(String name, String value) {
        info.getCommandExecutionFacade().execute(info, new ModifyCookieCommand(name, value));
    }

    /**
     * Closes and terminates the active instances of the browser and WebDriver.
     */
    public void quit() {
        if (info.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)) {
            info.testSucceeded();
        }

        info.getCommandExecutionFacade().execute(info, new QuitCommand());
    }

    /**
     * Closes the currently focused browser window.
     */
    public void close() {
        info.getCommandExecutionFacade().execute(info, new CloseCommand());
    }


    /**
     * Refreshes the current window in the browser.
     */
    public void refresh() {
        info.getCommandExecutionFacade().execute(info, new RefreshCommand());
    }

    /**
     * Changes the browser window size.
     *
     * @param size The new size of the browser window based off the enumerable BrowserSize.
     */
    public void resize(BrowserSize size) {
        info.getCommandExecutionFacade().execute(info, new ResizeCommand(size));
    }

    /**
     * Scrolls to the top of the current page.
     */
    public void scrollToTop() {
        info.getCommandExecutionFacade().execute(info, new ScrollToTopCommand());
    }

    /**
     * Scrolls to the bottom of the current page.
     */
    public void scrollToEnd() {
        info.getCommandExecutionFacade().execute(info, new ScrollToEndCommand());
    }

    /**
     * Replicates sending keyboard strokes to the alert in the current browser window.
     *
     * @param keys The values to send to the alert.
     */
    public void sendKeysToAlert(String keys) {
        info.getCommandExecutionFacade().execute(info, new SendKeysToAlertCommand(keys));
    }

    /**
     * Switches to the main window of the current browser.
     */
    public void switchToMainWindow() {
        switchToMainWindow(false);
    }

    /**
     * Switches to the main window of the current browser.
     *
     * @param waitForAllPopupsToClose Boolean switch to wait for all popup windows to be closed before switching,
     */
    public void switchToMainWindow(Boolean waitForAllPopupsToClose) {
        info.getCommandExecutionFacade().execute(info, new SwitchToMainWindowCommand(mainWindowHandle, waitForAllPopupsToClose));
    }

    /**
     * Switches to the window with the corresponding title.
     *
     * @param title The title of the window to switch to
     */
    public void switchToWindowByTitle(String title) {
        info.getCommandExecutionFacade().execute(info, new SwitchToWindowByTitleCommand(title));
    }

    /**
     * Switches to the window with the corresponding URL.
     *
     * @param url The URL of the window to switch to.
     */
    public void switchToWindowByUrl(String url) {
        info.getCommandExecutionFacade().execute(info, new SwitchToWindowByUrlCommand(url));
    }

    /**
     * Asserts that an alert is not present on the current page.
     */
    public void verifyAlertExists() {
        info.getCommandExecutionFacade().execute(info, new VerifyAlertExistsCommand());
    }

    /**
     * Asserts that an alert is present on the current page.
     */
    public void verifyAlertNotExists() {
        info.getCommandExecutionFacade().execute(info, new VerifyAlertNotExistsCommand());
    }

    /**
     * Asserts the description of the alert on the current page.
     *
     * @param comparingText The expected description on the alert.
     */
    public void verifyAlertText(String comparingText) {
        info.getCommandExecutionFacade().execute(info, new VerifyAlertTextCommand(comparingText));
    }

    /**
     * Function verifies that an alert gives the proper text.
     *
     * @param comparingText String to compare against Alert Text.
     * @param caseSensitive Determine caseSensitive comparison
     */
    public void verifyAlertTextLike(String comparingText, boolean caseSensitive) {
        info.getCommandExecutionFacade().execute(info, new VerifyAlertTextLikeCommand(comparingText, caseSensitive));
    }

    /**
     * Verifies the title given a string.
     *
     * @param comparingTitle the title, as a string, to verify.
     */
    public void verifyTitle(String comparingTitle) {
        info.getCommandExecutionFacade().execute(info, new VerifyTitleCommand(comparingTitle));
    }

    /**
     * Verifies the URL given a string.
     *
     * @param comparingURL the url, as a string, to compare.
     */
    public void verifyURL(String comparingURL) {
        info.getCommandExecutionFacade().execute(info, new VerifyUrlCommand(comparingURL));
    }

    /**
     * Function verifies that the window does not exist given a title as a string parameter.
     * @param windowTitle the title, as a string, to the test.
     */
    public void verifyWindowDoesNotExistByTitle(String windowTitle) {
        info.getCommandExecutionFacade().execute(info, new WindowDoesNotExistByTitleCommand(windowTitle));
    }

    /**
     * Function verifies that the window does not exist given a url as a string parameter.
     * @param url the url, as a string, to test by the function.
     */
    public void verifyWindowDoesNotExistByUrl(String url) {
        info.getCommandExecutionFacade().execute(info, new WindowDoesNotExistByUrlCommand(url));
    }

    /**
     * Function clicks all elements given an IBy selector.
     *
     * @param selector the selector for the element.
     */
    public void clickAllElementsCommand(IByWeb selector) {
        info.getCommandExecutionFacade().execute(
                info,
                new ClickAllElementsCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
