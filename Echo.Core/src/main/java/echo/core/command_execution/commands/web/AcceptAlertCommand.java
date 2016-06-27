package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;


/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Accepts an alert.
 */
public class AcceptAlertCommand extends Command {

    /**
     * Initializes a new instance of the AcceptAlertCommand.
     * @param log The logger.
     */
    public AcceptAlertCommand(ILog log) {
        super(log, Resources.getString("AcceptAlertCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     * @param driver The web driver.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).AcceptAlert(getGuid());
    }
}
