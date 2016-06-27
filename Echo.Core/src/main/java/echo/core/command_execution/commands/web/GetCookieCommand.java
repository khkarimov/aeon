package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 6/27/2016.
 */
public class GetCookieCommand extends CommandWithReturn {
    private String name;

    /**
     * Initializes a new instance of the <see cref="GetAllCookiesCommand"/> class.
     * @param log The logger.
     */
    public GetCookieCommand(ILog log, String name) {
        super(log, Resources.getString("GetCookieCommand_Info"));
        this.name = name;
    }

    @Override
    protected Object CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetCookie(getGuid(), name);
    }
}
