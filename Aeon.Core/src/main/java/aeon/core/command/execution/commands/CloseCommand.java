package aeon.core.command.execution.commands;

import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * <p>Closes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.Close();</p>
 */
public class CloseCommand extends Command {
    /**
     * Initializes a new instance of the {@link CloseCommand} class.
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
