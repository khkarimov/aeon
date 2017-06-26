package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Locks a mobile device.
 */
public class MobileLockCommand extends Command {

    private int seconds;

    /**
     * Initializes a new instance of the {@link MobileLockCommand} class.
     */
    public MobileLockCommand() {
        super(Resources.getString("MobileLockCommand_Info"));
        seconds = 0;
    }

    /**
     * Initializes a new instance of the {@link MobileLockCommand} class.
     * @param seconds Number of seconds the mobile device screen is locked for.
     */
    public MobileLockCommand(int seconds) {
        super(Resources.getString("MobileLockCommand_Info"));
        this.seconds = seconds;
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        if (seconds == 0) {
            ((IWebDriver) driver).mobileLock();
        } else {
            ((IWebDriver) driver).mobileLock(seconds);
        }

    }
}

