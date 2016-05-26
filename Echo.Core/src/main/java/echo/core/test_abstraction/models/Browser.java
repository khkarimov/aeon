package echo.core.test_abstraction.models;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.QuitCommand;
import echo.core.command_execution.commands.web.AddCookieCommand;
import echo.core.command_execution.commands.web.GoToUrlCommand;
import echo.core.common.helpers.URLUtil;

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

    public void Quit() {
        info.getCommandExecutionFacade().Execute(info, new QuitCommand(info.getLog()));
    }

}
