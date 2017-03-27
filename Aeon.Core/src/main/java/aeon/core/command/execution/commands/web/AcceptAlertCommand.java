package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;


/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Accepts an alert.
 */
public class AcceptAlertCommand extends Command {

    /**
     * Initializes a new instance of the AcceptAlertCommand.
     */
    public AcceptAlertCommand() {
        super(Resources.getString("AcceptAlertCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).acceptAlert();
    }
}
