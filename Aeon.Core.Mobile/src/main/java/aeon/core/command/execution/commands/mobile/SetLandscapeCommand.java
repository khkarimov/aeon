package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Sets the mobile device's orientation to landscape.
 */
public class SetLandscapeCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link SetLandscapeCommand} class.
     */
    public SetLandscapeCommand() {
        super(Resources.getString("MobileSetLandscapeCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileSetLandscape();
    }
}
