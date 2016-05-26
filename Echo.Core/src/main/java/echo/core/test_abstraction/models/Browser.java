package echo.core.test_abstraction.models;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.QuitCommand;
import echo.core.command_execution.commands.web.*;
import echo.core.common.helpers.URLUtil;
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
        info.getCommandExecutionFacade().Execute(info, new DeleteAllCookies(info.getLog()));
    }

    public void AcceptAlert() {
        info.getCommandExecutionFacade().Execute(info, new AcceptAlertCommand(info.getLog()));
    }

    public void DismissAlert() {info.getCommandExecutionFacade().Execute(info, new DismissAlertCommand(info.getLog()));}

    public void Maximize() {
        info.getCommandExecutionFacade().Execute(info, new MaximizeCommand(info.getLog()));
    }

    public void Quit() {
        info.getCommandExecutionFacade().Execute(info, new QuitCommand(info.getLog()));
    }

}
