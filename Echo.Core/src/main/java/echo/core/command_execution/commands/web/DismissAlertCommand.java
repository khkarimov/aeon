package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Dismisses an alert.
 */
public class DismissAlertCommand extends Command {

    /**
     * Initializes a new instance of the DismissAlertCommand.
     * @param log The logger.
     */
    public DismissAlertCommand (ILog log) {
        super(log, "DismissAlertCommand_Info");
    }

    /**
     * Provides the logic for the command.
     * @param driver The web driver.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DismissAlert(getGuid());
    }
}
