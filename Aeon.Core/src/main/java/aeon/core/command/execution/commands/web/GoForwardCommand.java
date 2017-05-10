package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Move forward a single entry in the browser's history.
 * Usage:
 *       Context.browser.goForward();
 *
 * Does nothing if we are on the latest page viewed.
 */
public class GoForwardCommand extends Command {

    /**
     * Initializes a new instance of the {@link GoForwardCommand} class.
     */
    public GoForwardCommand() {
        super(Resources.getString("GoForwardCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).goForward();
    }
}
