package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Scrolls to the top of the page.
 * Usage:
 * Context.browser.scrollToTop();
 */

public class ScrollToTopCommand extends Command {

    /**
     * Initializes a new instance of the {@link ScrollToTopCommand} class.
     */
    public ScrollToTopCommand() {
        super(Resources.getString("ScrollToTopCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).scrollToTop();
    }
}
