package echo.core.command_execution.commands.web;

/**
 * Created by SebastianR on 6/1/2016.
 */

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Asserts/Checks whether an alert exists. If your code makes an alert popup and after you close it you want to check whether it is the case this method will assert so.
 * Usage: Context.Browser.VerifyAlertNotExists()
 */
public class VerifyAlertNotExistsCommand extends Command {
    /**
     * Initializes a new instance of
     *
     * @param log
     * @see VerifyAlertNotExistsCommand class
     */
    public VerifyAlertNotExistsCommand(ILog log) {
        super(log, Resources.getString("VerifyAlertExistsCommand_Info"));
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).VerifyAlertNotExists(getGuid());
    }
}
