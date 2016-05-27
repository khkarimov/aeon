package echo.core.test_abstraction.models;

import com.sun.glass.ui.Size;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.QuitCommand;
import echo.core.command_execution.commands.web.AddCookieCommand;
import echo.core.command_execution.commands.web.GoToUrlCommand;
import echo.core.command_execution.commands.web.*;
import echo.core.command_execution.commands.web.ScrollToEndCommand;
import echo.core.command_execution.commands.web.ScrollToTopCommand;
import echo.core.common.helpers.URLUtil;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserSizeMap;
import echo.core.framework_abstraction.controls.web.IWebCookie;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class Browser {
    private AutomationInfo info;

    public Browser(AutomationInfo info) {
        this.info = info;
    }

    public void GoToUrl(String url) {
        info.getCommandExecutionFacade().Execute(info, new GoToUrlCommand(info.getLog(), URLUtil.CreateURL(url)));
    }

    public void GoBack() {info.getCommandExecutionFacade().Execute(info, new GoBackCommand(info.getLog()));}

    public void DeleteCookie(String cookie) {
        info.getCommandExecutionFacade().Execute(info, new DeleteCookieCommand(info.getLog(), cookie));
    }

    public void AddCookie(IWebCookie cookie) {
        info.getCommandExecutionFacade().Execute(info, new AddCookieCommand(info.getLog(), cookie));
    }

    public void DeleteAllCookies() {
        info.getCommandExecutionFacade().Execute(info, new DeleteAllCookiesCommand(info.getLog()));
    }

    public void Refresh() {
        info.getCommandExecutionFacade().Execute(info, new RefreshCommand(info.getLog()));
    }

    public void Resize(BrowserSize size){
        info.getCommandExecutionFacade().Execute(info, new ResizeCommand(info.getLog(), BrowserSizeMap.Map(size)));
    }

    public void AcceptAlert() {
        info.getCommandExecutionFacade().Execute(info, new AcceptAlertCommand(info.getLog()));
    }

    public void DismissAlert() {info.getCommandExecutionFacade().Execute(info, new DismissAlertCommand(info.getLog()));}

    public void Maximize() {
        info.getCommandExecutionFacade().Execute(info, new MaximizeCommand(info.getLog()));
    }

    public void GoForward() {
        info.getCommandExecutionFacade().Execute(info, new GoForwardCommand(info.getLog()));
    }

    public void Quit() {
        info.getCommandExecutionFacade().Execute(info, new QuitCommand(info.getLog()));
    }

    public void ScrollToTop(){
        info.getCommandExecutionFacade().Execute(info, new ScrollToTopCommand(info.getLog()));
    }

    public void ScrollToEnd(){
       info.getCommandExecutionFacade().Execute(info, new ScrollToEndCommand(info.getLog()));
    }
}
