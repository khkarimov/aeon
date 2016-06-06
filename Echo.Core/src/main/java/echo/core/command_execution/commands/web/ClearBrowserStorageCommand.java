package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by Steve Foo on 5/27/2016.
 */
public class ClearBrowserStorageCommand extends Command {

    /**
     * Initializes a new instance of the <see cref="ClearBrowserStorageCommand"/> class.
     *
     * @param log The logger.
     */
    public ClearBrowserStorageCommand(ILog log) {
        super(log, Resources.getString("ClearBrowserStorageCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).ClearBrowserStorage(getGuid());
    }

}
