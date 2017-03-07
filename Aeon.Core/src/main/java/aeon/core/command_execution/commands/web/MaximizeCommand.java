package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Maximizes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.Maximize();</p>
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

        ((IWebDriver) driver).Maximize(getGuid());
    }
}
