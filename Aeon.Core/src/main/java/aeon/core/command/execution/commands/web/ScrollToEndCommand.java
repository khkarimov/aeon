package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Scrolls to the end of the page.
 * Usage:
 * Context.browser.scrollToEnd();
 */

public class ScrollToEndCommand extends Command {

    /**
     * Initializes a new instance of the {@link ScrollToTopCommand} class.
     */
    public ScrollToEndCommand() {
        super(Resources.getString("ScrollToEndCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).scrollToEnd();
    }
}
