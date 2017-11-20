package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

/**
 * Sets the mobile device's orientation to portrait.
 */
public class MobileSetPortraitCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link MobileSetPortraitCommand} class.
     */
    public MobileSetPortraitCommand() {
        super(Resources.getString("MobileSetPortraitCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileAppDriver driver) {
        driver.mobileSetPortrait();
    }
}
