package aeon.core.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileAppDriver;

/**
 * Hides the keyboard on a mobile device.
 */
public class MobileHideKeyboardCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link MobileHideKeyboardCommand} class.
     */
    public MobileHideKeyboardCommand() {
        super(Resources.getString("MobileHideKeyboardCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileAppDriver driver) {
        driver.mobileHideKeyboard();
    }
}
