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
 * Deletes all cookies of the command.
 */
public class DeleteAllCookiesCommand extends Command {

    /**
     * Initializes a new instance of the DeleteAllCookiesCommand.
     *
     * @param log The logger.
     */
    public DeleteAllCookiesCommand(ILog log) {
        super(log, Resources.getString("DeleteAllCookiesCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).DeleteAllCookies(getGuid());
    }
}
