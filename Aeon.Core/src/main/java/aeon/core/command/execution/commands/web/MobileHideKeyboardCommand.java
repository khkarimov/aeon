package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Hides the keyboard on a mobile device.
 */
public class MobileHideKeyboardCommand extends Command {

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
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver)driver).mobileHideKeyboard();
    }
}