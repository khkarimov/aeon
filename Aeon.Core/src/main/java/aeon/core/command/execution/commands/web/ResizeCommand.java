package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import com.sun.glass.ui.Size;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.BrowserSizeMap;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Resizes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.Resize(BrowserSize.Maximized);</p>
 * <p>      Context.browser.Resize(800, 600);</p>
 */
public class ResizeCommand extends Command {
    private Size size;

    /**
     * Initializes a new instance of the {@link ResizeCommand} class.
     *
     * @param log  The logger.
     * @param size The new browser size.
     */
    public ResizeCommand(ILog log, BrowserSize size) {
        super(log, Resources.getString("ResizeCommand_Info"));
        this.size = BrowserSizeMap.Map(size);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        ((IWebDriver) driver).Resize(getGuid(), size);
    }
}
