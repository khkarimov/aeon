package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Move forward a single entry in the browser's history.</p>
 * <p>Usage:</p>
 * <p>      Context.browser.GoForward();</p>
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
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).GoForward();
    }
}
