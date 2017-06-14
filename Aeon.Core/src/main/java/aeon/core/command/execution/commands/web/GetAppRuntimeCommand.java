package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Gets the corresponding enumerable BrowserType associated with the current browser.
 */
public class GetBrowserTypeCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetBrowserTypeCommand} class.
     */
    public GetBrowserTypeCommand() {
        super(Resources.getString("GetBrowserTypeCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The enumerable BrowserType associated with the browser.
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getBrowserType();
    }
}
