package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IWebCookie;
import echo.core.framework_abstraction.IWebDriver;

/**
 * Returns the name value pair for a cookie.
 */
public class AddCookieCommand extends Command {
    private IWebCookie cookie;

    /**
     * Initializes a new instance of the <see cref="AddCookieCommand"/> class.
     *
     * @param log    The logger.
     * @param cookie Cookie to be added.
     */
    public AddCookieCommand(ILog log, IWebCookie cookie) {
        super(log, "Adding cookie");
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
