package echo.core.command_execution.commands.web;

import com.sun.glass.ui.Size;
import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserSizeMap;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

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
