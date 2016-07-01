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
public class GetBrowserTypeCommand extends CommandWithReturn {

    public GetBrowserTypeCommand(ILog log) {
        super(log, Resources.getString("GetBrowserTypeCommand_Info"));
    }

    @Override
    protected Object CommandDelegate(IDriver driver) {
        return (BrowserType) ((IWebDriver) driver).GetBrowserType(getGuid());
    }
}
