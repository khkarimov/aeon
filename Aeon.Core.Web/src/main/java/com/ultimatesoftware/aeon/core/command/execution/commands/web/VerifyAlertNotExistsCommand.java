package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Asserts/Checks whether an alert exists. If your code makes an alert popup and after you close it you want to check whether it is the case this method will assert so.
 * Usage: Context.browser.verifyAlertNotExists()
 */
public class VerifyAlertNotExistsCommand extends Command {

    /**
     * Initializes a new instance of {@link VerifyAlertNotExistsCommand}.
     *
     * @see VerifyAlertNotExistsCommand class
     */
    public VerifyAlertNotExistsCommand() {
        super(Resources.getString("VerifyAlertNotExistsCommand_Info"));
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).verifyAlertNotExists();
    }
}
