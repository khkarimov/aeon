package aeon.core.command.execution.commands;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;

/**
 * <p>Closes the currently focused browser window.</p>
 * <p>Usage:</p>
 * <p>      Context.browser.close();</p>
 */
public class CloseCommand extends Command {
    /**
     * Initializes a new instance of the {@link CloseCommand} class.
     *
     */
    public CloseCommand() {
        super(Resources.getString("CloseCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.close();
    }
}
