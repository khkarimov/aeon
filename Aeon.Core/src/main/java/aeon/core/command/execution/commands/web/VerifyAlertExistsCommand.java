package aeon.core.command.execution.commands.web;

/**
 * Created By SebastianR on 6/1/2016.
 */

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Asserts/Checks whether an alert exists. If your code makes an alert popup and you want to check whether it is the case this method will assert so.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Alert.exists();</p>
 */

public class VerifyAlertExistsCommand extends Command {
    /**
     * Initializes a new instance of the
     * @see VerifyAlertExistsCommand class
     */
    public VerifyAlertExistsCommand() {
        super(Resources.getString("VerifyAlertExistsCommand_Info"));
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).verifyAlertExists();
    }
}
