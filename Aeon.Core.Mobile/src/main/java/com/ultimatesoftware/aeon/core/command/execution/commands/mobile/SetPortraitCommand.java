package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

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
