package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 5/26/2016.
 */
public class DeleteCookieCommand extends Command {
    private String cookie;

    /**
     * Initializes a new instance of the <see cref="DeleteCookieCommand"/> class.
     *
     * * @param log    The logger.
     * @param cookie Name of the cookie to be deleted.
    */
    public DeleteCookieCommand(ILog log, String cookie) {
        super(log, Resources.getString("DeleteCookieCommand_Info"));
        this.cookie = cookie;
    }

    /**
     * The method that provides the logic for the command
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).DeleteCookie(getGuid(), this.cookie);
    }
}
