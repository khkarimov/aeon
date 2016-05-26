package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;


/**
 * Created by RafaelT on 5/26/2016.
 */
public class AcceptAlertCommand extends Command {

    public AcceptAlertCommand(ILog log) {
        super(log, "Accepting Alert");
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).AcceptAlert(getGuid());
    }
}
