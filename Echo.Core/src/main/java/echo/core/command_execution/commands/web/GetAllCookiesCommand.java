package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 6/27/2016.
 */

/**
 * Returns all of the browsers cookies
 */
public class GetAllCookiesCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetAllCookiesCommand} class.
     * @param log The logger.
     */
    public GetAllCookiesCommand(ILog log) {
        super(log, Resources.getString("GetAllCookiesCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     * @param driver The framework abstraction facade.
     * @return Returns a Collection of {@link echo.core.framework_abstraction.controls.web.IWebCookie}
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetAllCookies(getGuid());
    }
}
