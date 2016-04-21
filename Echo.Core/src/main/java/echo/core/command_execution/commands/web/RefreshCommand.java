package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Refreshes the current page
 */
public class RefreshCommand extends Command {

    /**
     * Initializes a new instance of RefreshCommand
     *
     * @param log the logger
     */
    public RefreshCommand(ILog log) {
        super(log, Resources.getString("RefreshCommand_Info"));
    }

    /**
     * The method which provides the logic for the command
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException();
        }

        ((IWebDriver) driver).Refresh(getGuid());
    }
}
