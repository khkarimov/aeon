package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;

import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;


public class MobileLockCommand extends Command {

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     */
    public MobileLockCommand() {
            super(Resources.getString("MobileLockCommand_Info"));
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).mobileLock();
    }
}
