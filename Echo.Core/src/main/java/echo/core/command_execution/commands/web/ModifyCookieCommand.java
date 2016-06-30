package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 6/27/2016.
 */

/**
 * Modifies an existing cookie.
 */
public class ModifyCookieCommand extends Command {
    private String name;
    private String value;

    /**
     * Initializes a new instance of the ModifyCookieCommand.
     * @param log The logger.
     * @param name The name of the cookie.
     * @param value The value.
     */
    public ModifyCookieCommand(ILog log, String name, String value) {
        super(log, Resources.getString("ModifyCookieCommand_Info"));
        this.name = name;
        this.value = value;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).ModifyCookie(getGuid(), name, value);
    }
}
