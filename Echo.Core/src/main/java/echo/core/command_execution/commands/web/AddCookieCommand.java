package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Returns the name value pair for a cookie.
 */
public class AddCookieCommand extends Command {
    private IWebCookie cookie;

    /**
     * Initializes a new instance of the AddCookieCommand class.
     *
     * @param log    The logger.
     * @param cookie Cookie to be added.
     */
    public AddCookieCommand(ILog log, IWebCookie cookie) {
        super(log, String.format(Locale.getDefault(), Resources.getString("AddCookieCommand_Info"), cookie.getName()));
        this.cookie = cookie;
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

        ((IWebDriver) driver).AddCookie(getGuid(), cookie);
    }
}
