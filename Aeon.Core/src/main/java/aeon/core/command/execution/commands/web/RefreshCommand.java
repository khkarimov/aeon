package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Refreshes the current page
 */
public class RefreshCommand extends Command {

    /**
     * Initializes a new instance of RefreshCommand
     * the logger
     */
    public RefreshCommand() {
        super(Resources.getString("RefreshCommand_Info"));
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
