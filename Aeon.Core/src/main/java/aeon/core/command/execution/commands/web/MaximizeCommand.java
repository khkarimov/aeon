package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Maximizes the currently focused browser window.</p>
 * <p>Usage:</p>
 * <p>      Context.browser.maximize();</p>
 */
public class MaximizeCommand extends Command {
    /**
     * Initializes a new instance of the MaximizeCommand class.
     */
    public MaximizeCommand() {
        super(Resources.getString("MaximizeCommand_Info"));
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

        ((IWebDriver) driver).maximize();
    }
}
