package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.BrowserType;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 7/1/2016.
 */

/**
 * Gets the corresponding enumerable BrowserType associated with the current browser.
 */
public class GetBrowserTypeCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the GetBrowserTypeCommand.
     * @param log The logger.
     */
    public GetBrowserTypeCommand(ILog log) {
        super(log, Resources.getString("GetBrowserTypeCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     * @param driver The framework abstraction facade.
     * @return The enumerable BrowserType associated with the browser.
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        return (BrowserType) ((IWebDriver) driver).GetBrowserType(getGuid());
    }
}
