package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Maximizes the currently focused browser window.
 * Usage:
 * Context.browser.maximize();
 */
public class MaximizeCommand extends Command {

    /**
     * Initializes a new instance of the {@link MaximizeCommand} class.
     */
    public MaximizeCommand() {
        super(Resources.getString("MaximizeCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).maximize();
    }
}
