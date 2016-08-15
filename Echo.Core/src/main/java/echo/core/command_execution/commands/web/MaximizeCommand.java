package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Maximizes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.Maximize();</p>
 */
public class MaximizeCommand extends Command {
    /**
     * Initializes a new instance of the MaximizeCommand class.
     *
     * @param log The logger.
     */
    public MaximizeCommand(ILog log) {
        super(log, Resources.getString("MaximizeCommand_Info"));
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

        ((IWebDriver)driver).Maximize(getGuid());
    }
}
