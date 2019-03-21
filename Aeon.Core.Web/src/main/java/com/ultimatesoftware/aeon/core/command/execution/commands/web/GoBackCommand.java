package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Move back a single entry in the browser's history.
 * Usage:
 *       Context.browser.goBack();
 */
public class GoBackCommand extends Command {

    /**
     * Initializes a new instance of the {@link GoBackCommand} class.
     */
    public GoBackCommand() {
        super(Resources.getString("GoBackCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).goBack();
    }
}
