package aeon.core.command.execution.commands.mobile;

import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IMobileDriver;

/**
 * Hides the keyboard on a mobile device.
 */
public class HideKeyboardCommand extends MobileCommand {

    /**
     * Initializes a new instance of the {@link HideKeyboardCommand} class.
     */
    public HideKeyboardCommand() {
        super(Resources.getString("MobileHideKeyboardCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     */
    @Override
    protected void driverDelegate(IMobileDriver driver) {
        driver.mobileHideKeyboard();
    }
}
