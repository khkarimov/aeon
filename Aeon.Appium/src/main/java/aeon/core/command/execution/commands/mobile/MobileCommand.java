package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Serves as the base class for all web element commands that need a finder.
 */
public abstract class MobileCommand extends Command {

    /**
     * Initializes a new instance of the {@link MobileCommand} class.
     *
     * @param message The message to log.
     */
    protected MobileCommand(String message) {
        super(message);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    abstract void driverDelegate(IMobileAppDriver driver);

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        driverDelegate((IMobileAppDriver) driver);
    }
}
