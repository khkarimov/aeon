package aeon.selenium.appium.command.execution.commands.mobile;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.selenium.appium.framework.abstraction.drivers.IMobileAppDriver;

/**
 * Sets the mobile device's orientation to portrait.
 */
public class MobileSetPortraitCommand extends Command {

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
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IMobileAppDriver) driver).mobileSetPortrait();
    }
}
