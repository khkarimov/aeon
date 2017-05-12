package aeon.core.command.execution.commands.web;

/**
 * Created by SebastianR on 6/1/2016.
 */

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Asserts/Checks whether an alert exists. If your code makes an alert popup and after you close it you want to check whether it is the case this method will assert so.
 * Usage: Context.browser.verifyAlertNotExists()
 */
public class VerifyAlertNotExistsCommand extends Command {

    /**
     * Initializes a new instance of
     *
     * @see VerifyAlertNotExistsCommand class
     */
    public VerifyAlertNotExistsCommand() {
        super(Resources.getString("VerifyAlertNotExistsCommand_Info"));
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).verifyAlertNotExists();
    }
}
