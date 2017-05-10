package aeon.core.command.execution.commands.web;

import com.sun.glass.ui.Size;
import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.BrowserSizeMap;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Resize the currently focused browser window.
 * Usage:
 *       Context.browser.resize(BrowserSize.Maximized);
 *       Context.browser.resize(800, 600);
 */
public class ResizeCommand extends Command {

    private Size size;

    /**
     * Initializes a new instance of the {@link ResizeCommand} class.
     *
     * @param size The new browser size.
     */
    public ResizeCommand(BrowserSize size) {
        super(Resources.getString("ResizeCommand_Info"));
        this.size = BrowserSizeMap.map(size);
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

        ((IWebDriver) driver).resize(size);
    }
}
