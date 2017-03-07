package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.CommandWithReturn;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/27/2016.
 */

/**
 * Gets a specific cookie.
 */
public class GetCookieCommand extends CommandWithReturn {
    private String name;

    /**
     * Initializes a new instance of the {@link GetCookieCommand} class.
     *
     * @param log  The logger.
     * @param name The name of the cookie to get.
     */
    public GetCookieCommand(ILog log, String name) {
        super(log, String.format(Locale.getDefault(), Resources.getString("GetCookieCommand_Info"), name));
        this.name = name;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return A IWebCookie representing the cookie.
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetCookie(getGuid(), name);
    }
}
