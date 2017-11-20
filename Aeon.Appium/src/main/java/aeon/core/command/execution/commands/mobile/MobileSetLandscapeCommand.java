package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

/**
 * Sets the mobile device's orientation to landscape.
 */
public class MobileSetLandscapeCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link MobileSetLandscapeCommand} class.
     */
    public MobileSetLandscapeCommand() {
        super(Resources.getString("MobileSetLandscapeCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileAppDriver driver) {
        driver.mobileSetLandscape();
    }
}
