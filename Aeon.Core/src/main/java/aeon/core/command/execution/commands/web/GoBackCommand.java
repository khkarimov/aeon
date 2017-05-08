package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Move back a single entry in the browser's history.</p>
 * <p>Usage:</p>
 * <p>      Context.browser.goBack();</p>
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
