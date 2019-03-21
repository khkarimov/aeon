package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.QuitCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.*;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;

import java.util.Collection;

/**
 * Browser class.
 */
public class Browser {

    private AutomationInfo automationInfo;
    private String mainWindowHandle;

    /**
     * The constructor for the Browser given an AutomationInfo object.
     *
     * @param automationInfo sets the automation info of the newly made Browser.
     */
    public Browser(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    /**
     * Function to accept an incoming alert.
     */
    public void acceptAlert() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new AcceptAlertCommand());
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public void addCookie(IWebCookie cookie) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new AddCookieCommand(cookie));
    }

    /**
     * Deletes all the cookies.
     */
    public void deleteAllCookies() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DeleteAllCookiesCommand());
    }

    /**
     * Deletes a cookie.
     *
     * @param cookie The name of the cookie to be deleted.
     */
    public void deleteCookie(String cookie) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DeleteCookieCommand(cookie));
    }

    /**
     * Dismisses an alert on a page.
     */
    public void dismissAlert() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new DismissAlertCommand());
    }

    /**
     * Gets the list of all the cookies.
     *
     * @return A collection of all the cookies in the browser.
     */
    public Collection<IWebCookie> getAllCookies() {
        return (Collection<IWebCookie>) automationInfo.getCommandExecutionFacade().execute(automationInfo, new GetAllCookiesCommand());
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return The BrowserType for the current browser.
     */
    public IBrowserType getBrowserType() {
        return ((WebConfiguration) automationInfo.getConfiguration()).getBrowserType();
    }

    /**
     * Get the body text of the alert that is raised on a page.
     *
     * @return The description of the alert dialog box.
     */
    public String getAlertText() {
        return (String) automationInfo.getCommandExecutionFacade().execute(automationInfo, new GetAlertTextCommand());
    }

    /**
     * Gets a cookie.
     *
     * @param name The name of the cookie to be retrieved.
     * @return The specified cookie.
     */
    public IWebCookie getCookie(String name) {
        return (IWebCookie) automationInfo.getCommandExecutionFacade().execute(automationInfo, new GetCookieCommand(name));
    }

    /**
     * Access the history of the browser to execute the Back function.
     */
    public void goBack() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new GoBackCommand());
    }

    /**
     * Access the history of the browser to execute the Forward function.
     */
    public void goForward() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new GoForwardCommand());
    }

    /**
     * Navigates the browser the URL.
     *
     * @param url The URL the navigate to.
     */
    public void goToUrl(String url) {
        mainWindowHandle = (String) automationInfo.getCommandExecutionFacade().execute(automationInfo, new GoToUrlCommand(url));
    }

    /**
     * Maximizes the browser window.
     */
    public void maximize() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new MaximizeCommand());
    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public void modifyCookie(String name, String value) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ModifyCookieCommand(name, value));
    }

    /**
     * Closes and terminates the active instances of the browser and WebDriver.
     */
    public void quit() {
        if (automationInfo.getConfiguration().getBoolean(Configuration.Keys.REPORTING, true)) {
            automationInfo.testSucceeded();
        }

        automationInfo.getCommandExecutionFacade().execute(automationInfo, new QuitCommand());
    }

    /**
     * Closes the currently focused browser window.
     */
    public void close() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new CloseCommand());
    }


    /**
     * Refreshes the current window in the browser.
     */
    public void refresh() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new RefreshCommand());
    }

    /**
     * Changes the browser window size.
     *
     * @param size The new size of the browser window based off the enumerable BrowserSize.
     */
    public void resize(BrowserSize size) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ResizeCommand(size.toString()));
    }

    /**
     * Scrolls to the top of the current page.
     */
    public void scrollToTop() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ScrollToTopCommand());
    }

    /**
     * Scrolls to the bottom of the current page.
     */
    public void scrollToEnd() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new ScrollToEndCommand());
    }

    /**
     * Replicates sending keyboard strokes to the alert in the current browser window.
     *
     * @param keys The values to send to the alert.
     */
    public void sendKeysToAlert(String keys) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SendKeysToAlertCommand(keys));
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
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToMainWindowCommand(mainWindowHandle, waitForAllPopupsToClose));
    }

    /**
     * Switches to the window with the corresponding title.
     *
     * @param title The title of the window to switch to
     */
    public void switchToWindowByTitle(String title) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWindowByTitleCommand(title));
    }

    /**
     * Switches to the window with the corresponding URL.
     *
     * @param url The URL of the window to switch to.
     */
    public void switchToWindowByUrl(String url) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new SwitchToWindowByUrlCommand(url));
    }

    /**
     * Asserts that an alert is not present on the current page.
     */
    public void verifyAlertExists() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyAlertExistsCommand());
    }

    /**
     * Asserts that an alert is present on the current page.
     */
    public void verifyAlertNotExists() {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyAlertNotExistsCommand());
    }

    /**
     * Asserts the description of the alert on the current page.
     *
     * @param comparingText The expected description on the alert.
     */
    public void verifyAlertText(String comparingText) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyAlertTextCommand(comparingText));
    }

    /**
     * Function verifies that an alert gives the proper text.
     *
     * @param comparingText String to compare against Alert Text.
     * @param caseSensitive Determine caseSensitive comparison
     */
    public void verifyAlertTextLike(String comparingText, boolean caseSensitive) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyAlertTextLikeCommand(comparingText, caseSensitive));
    }

    /**
     * Verifies the title given a string.
     *
     * @param comparingTitle the title, as a string, to verify.
     */
    public void verifyTitle(String comparingTitle) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyTitleCommand(comparingTitle));
    }

    /**
     * Verifies the URL given a string.
     *
     * @param comparingURL the url, as a string, to compare.
     */
    public void verifyURL(String comparingURL) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new VerifyUrlCommand(comparingURL));
    }

    /**
     * Function verifies that the window does not exist given a title as a string parameter.
     *
     * @param windowTitle the title, as a string, to the test.
     */
    public void verifyWindowDoesNotExistByTitle(String windowTitle) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new WindowDoesNotExistByTitleCommand(windowTitle));
    }

    /**
     * Function verifies that the window does not exist given a url as a string parameter.
     *
     * @param url the url, as a string, to test by the function.
     */
    public void verifyWindowDoesNotExistByUrl(String url) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo, new WindowDoesNotExistByUrlCommand(url));
    }

    /**
     * Function clicks all elements given an IBy selector.
     *
     * @param selector the selector for the element.
     */
    public void clickAllElementsCommand(IByWeb selector) {
        automationInfo.getCommandExecutionFacade().execute(
                automationInfo,
                new ClickAllElementsCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new IByWeb[0])));
    }
}
