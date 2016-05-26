package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */
public class DismissAlertCommand extends Command {

    public DismissAlertCommand (ILog log) {
        super(log, "DismissAlertCommand_Info");
    }
    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DismissAlert(getGuid());
    }
}
