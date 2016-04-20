package echo.core.command_execution.commands;

import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;

/**
 * <p>Closes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.Close();</p>
 */
public class CloseCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="CloseCommand"/> class.
     *
     * @param log The logger.
     */
    public CloseCommand(ILog log) {
        super(log, Resources.getString("CloseCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.Close(getGuid());
    }
}
