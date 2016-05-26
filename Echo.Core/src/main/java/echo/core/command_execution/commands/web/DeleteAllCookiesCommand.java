package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */
public class DeleteAllCookiesCommand extends Command {

    public DeleteAllCookiesCommand (ILog log) {
        super(log, Resources.getString("DeleteAllCookieCommand_Info"));
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DeleteAllCookies(getGuid());
    }
}
