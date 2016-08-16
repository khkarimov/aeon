package echo.core.test_abstraction.models;


import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.QuitCommand;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.AddCookieCommand;
import echo.core.command_execution.commands.web.GoToUrlCommand;
import echo.core.command_execution.commands.web.*;
import echo.core.command_execution.commands.web.ScrollToEndCommand;
import echo.core.command_execution.commands.web.ScrollToTopCommand;
import echo.core.common.helpers.URLUtil;
import echo.core.common.logging.ILog;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.IWebCookie;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class Browser {
    private AutomationInfo info;

    public Browser(AutomationInfo info) {
        this.info = info;
    }

    public void AcceptAlert() {
        info.getCommandExecutionFacade().Execute(info, new AcceptAlertCommand(info.getLog()));
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    public void AddCookie(IWebCookie cookie) {
        info.getCommandExecutionFacade().Execute(info, new AddCookieCommand(info.getLog(), cookie));
    }

    /**
     * Deletes all the cookies.
     */
    public void DeleteAllCookies() {
        info.getCommandExecutionFacade().Execute(info, new DeleteAllCookiesCommand(info.getLog()));
    }

    /**
     * Deletes a cookie.
     * @param cookie The name of the cookie to be deleted.
     */
    public void DeleteCookie(String cookie) {
        info.getCommandExecutionFacade().Execute(info, new DeleteCookieCommand(info.getLog(), cookie));
    }

    /**
     * Dismisses an alert on a page.
     */
    public void DismissAlert() {
        info.getCommandExecutionFacade().Execute(info, new DismissAlertCommand(info.getLog()));
    }

    /**
     * Gets the list of all the cookies.
     * @return A collection of all the cookies in the browser.
     */
    public Collection<IWebCookie> GetAllCookies() {
        return (Collection<IWebCookie>) info.getCommandExecutionFacade().Execute(info, new GetAllCookiesCommand(info.getLog()));
    }

    /**
     * Returns the enumerable BrowserType representing the current browser.
     * @return The BrowserType for the current browser.
     */
    public BrowserType GetBrowserType() {
        return (BrowserType) info.getCommandExecutionFacade().Execute(info, new GetBrowserTypeCommand(info.getLog()));
    }

    /**
     *Get the body text of the alert that is raised on a page.
     * @return The description of the alert dialog box.
     */
    public String GetAlertText() {
        return info.getCommandExecutionFacade().Execute(info, new GetAlertTextCommand(info.getLog())).toString();
    }

    /**
     * Gets a cookie.
     * @param name The name of the cookie to be retrieved.
     * @return The specified cookie.
     */
    public IWebCookie GetCookie(String name) {
        return (IWebCookie) info.getCommandExecutionFacade().Execute(info, new GetCookieCommand(info.getLog(), name));
    }

    /**
     * Access the history of the browser to execute the Back function.
     */
    public void GoBack() {
        info.getCommandExecutionFacade().Execute(info, new GoBackCommand(info.getLog()));
    }

    /**
     * Access the history of the browser to execute the Forward function.
     */
    public void GoForward() {
        info.getCommandExecutionFacade().Execute(info, new GoForwardCommand(info.getLog()));
    }

    /**
     * Navigates the browser the URL.
     * @param url The URL the navigate to.
     */
    public void GoToUrl(String url) {
        info.getCommandExecutionFacade().Execute(info, new GoToUrlCommand(info.getLog(), URLUtil.CreateURL(url)));
    }

    /**
     * Maximizes the browser window.
     */
    public void Maximize() {
        info.getCommandExecutionFacade().Execute(info, new MaximizeCommand(info.getLog()));
    }

    /**
     * Modifies the value of a cookie.
     * @param name The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    public void ModifyCookie(String name, String value){
        info.getCommandExecutionFacade().Execute(info, new ModifyCookieCommand(info.getLog(), name, value));
    }

    /**
     * Closes and terminates the active instances of the browser and WebDriver.
     */
    public void Quit() {
        info.getCommandExecutionFacade().Execute(info, new QuitCommand(info.getLog()));
    }

    /**
     * Refreshes the current window in the browser.
     */
    public void Refresh() {
        info.getCommandExecutionFacade().Execute(info, new RefreshCommand(info.getLog()));
    }

    /**
     * Changes the browser window size.
     * @param size The new size of the browser window based off the enumerable BrowserSize.
     */
    public void Resize(BrowserSize size){
        info.getCommandExecutionFacade().Execute(info, new ResizeCommand(info.getLog(), size));
    }

    /**
     * Scrolls to the top of the current page.
     */
    public void ScrollToTop(){
        info.getCommandExecutionFacade().Execute(info, new ScrollToTopCommand(info.getLog()));
    }

    /**
     * Scrolls to the bottom of the current page.
     */
    public void ScrollToEnd(){
        info.getCommandExecutionFacade().Execute(info, new ScrollToEndCommand(info.getLog()));
    }

    /**
     * Replicates sending keyboard strokes to the alert in the cirrent browser window.
     * @param keys The values to send to the alert.
     */
    public void SendKeysToAlert(String keys){
        info.getCommandExecutionFacade().Execute(info, new SendKeysToAlertCommand(info.getLog(), keys));
    }

    /**
     * Switches to the window with the corresponding title.
     * @param title The title of the window to switch to
     */
    public void SwitchToWindowByTitle(String title) {
        info.getCommandExecutionFacade().Execute(info, new SwitchToWindowByTitleCommand(info.getLog(), title));
    }

    /**
     * Switches to the window with the corresponding URL.
     * @param url The URL of the window to switch to.
     */
    public void SwitchToWindowByUrl(String url){
        info.getCommandExecutionFacade().Execute(info, new SwitchToWindowByUrlCommand(info.getLog(), url));
    }

    /**
     * Asserts that an alert is not present on the current page.
     */
    public void VerifyAlertExists(){
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertExistsCommand(info.getLog()));
    }

    /**
     * Asserts that an alert is present on the current page.
     */
    public void VerifyAlertNotExists(){
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertNotExistsCommand(info.getLog()));
    }

    /**
     * Asserts the description of the alert on the current page.
     * @param comparingText The expected description on the alert.
     */
    public void VerifyAlertText(String comparingText){
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertTextCommand(info.getLog(), comparingText));
    }

    /**
     *
     * @param comparingText
     * @param caseSensitive
     */
    public void VerifyAlertTextLike(String comparingText, boolean caseSensitive){
        info.getCommandExecutionFacade().Execute(info, new VerifyAlertTextLikeCommand(info.getLog(), comparingText, caseSensitive));
    }

    public void VerifyTitle(String comparingTitle){
        info.getCommandExecutionFacade().Execute(info, new VerifyTitleCommand(info.getLog(), comparingTitle));
    }

    public void VerifyURL(String comparingURL){
        info.getCommandExecutionFacade().Execute(info, new VerifyUrlCommand(info.getLog(), comparingURL));
    }

    public void VerifyWindowDoesNotExistByTitle(String windowTitle){
        info.getCommandExecutionFacade().Execute(info, new WindowDoesNotExistByTitleCommand(info.getLog(), windowTitle));
    }

    public void VerifyWindowDoesNotExistByUrl(String url){
        info.getCommandExecutionFacade().Execute(info, new WindowDoesNotExistByUrlCommand(info.getLog(), url));
    }

    public void ClickAllElementsCommand(IBy selector){
        info.getCommandExecutionFacade().Execute(
                info,
                new ClickAllElementsCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
