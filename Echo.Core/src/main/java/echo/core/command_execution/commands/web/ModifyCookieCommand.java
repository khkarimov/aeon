package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

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
     *
     * @param log   The logger.
     * @param name  The name of the cookie.
     * @param value The value.
     */
    public ModifyCookieCommand(ILog log, String name, String value) {
        super(log, String.format(Locale.getDefault(), Resources.getString("ModifyCookieCommand_Info"), name, value));
        this.name = name;
        this.value = value;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).ModifyCookie(getGuid(), name, value);
    }
}
