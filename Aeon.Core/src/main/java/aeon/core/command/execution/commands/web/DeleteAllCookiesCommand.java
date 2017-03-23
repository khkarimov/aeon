package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Deletes all cookies of the command.
 */
public class DeleteAllCookiesCommand extends Command {

    /**
     * Initializes a new instance of the DeleteAllCookiesCommand.
     */
    public DeleteAllCookiesCommand() {
        super(Resources.getString("DeleteAllCookiesCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).deleteAllCookies();
    }
}
