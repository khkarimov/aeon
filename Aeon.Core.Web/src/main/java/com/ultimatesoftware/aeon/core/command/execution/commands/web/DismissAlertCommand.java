package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Dismisses an alert.
 */
public class DismissAlertCommand extends Command {

    /**
     * Initializes a new instance of the {@link DismissAlertCommand} class.
     */
    public DismissAlertCommand() {
        super(Resources.getString("DismissAlertCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The web driver.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).dismissAlert();
    }
}
