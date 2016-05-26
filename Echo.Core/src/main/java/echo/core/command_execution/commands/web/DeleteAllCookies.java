package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by Administrator on 5/26/2016.
 */
public class DeleteAllCookies extends Command {

    public DeleteAllCookies (ILog log) {
        super(log, "Deleting all cookies");
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DeleteAllCookies(getGuid());
    }
}
