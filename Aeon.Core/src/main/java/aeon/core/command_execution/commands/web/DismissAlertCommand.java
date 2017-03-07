package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Dismisses an alert.
 */
public class DismissAlertCommand extends Command {

    /**
     * Initializes a new instance of the DismissAlertCommand.
     *
     * @param log The logger.
     */
    public DismissAlertCommand(ILog log) {
        super(log, Resources.getString("DismissAlertCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DismissAlert(getGuid());
    }
}
