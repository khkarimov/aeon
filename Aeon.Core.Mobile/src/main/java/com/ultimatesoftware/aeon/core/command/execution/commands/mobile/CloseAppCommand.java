package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Command that closes current running application by pressing home key. App will stay running
 * in the background in the state that it was exited (app state will not be reset).
 */
public class CloseAppCommand extends MobileCommand{

    /**
     * Constructor for the command.
     */
    public CloseAppCommand() {
        super(Resources.getString("CloseAppCommand_Info"));
    }

    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.closeApp();
    }
}
