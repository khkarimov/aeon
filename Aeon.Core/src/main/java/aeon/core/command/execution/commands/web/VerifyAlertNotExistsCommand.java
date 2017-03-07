package aeon.core.command.execution.commands.web;

/**
 * Created by SebastianR on 6/1/2016.
 */

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Asserts/Checks whether an alert exists. If your code makes an alert popup and after you close it you want to check whether it is the case this method will assert so.
 * Usage: Context.browser.VerifyAlertNotExists()
 */
public class VerifyAlertNotExistsCommand extends Command {
    /**
     * Initializes a new instance of
     *
     * @param log
     * @see VerifyAlertNotExistsCommand class
     */
    public VerifyAlertNotExistsCommand(ILog log) {
        super(log, Resources.getString("VerifyAlertNotExistsCommand_Info"));
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertNotExists(getGuid());
    }
}
