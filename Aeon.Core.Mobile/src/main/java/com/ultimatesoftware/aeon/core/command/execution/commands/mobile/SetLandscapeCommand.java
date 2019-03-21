package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

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
