package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Sets the mobile device's orientation to portrait.
 */
public class SetPortraitCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link SetPortraitCommand} class.
     */
    public SetPortraitCommand() {
        super(Resources.getString("MobileSetPortraitCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileSetPortrait();
    }
}
