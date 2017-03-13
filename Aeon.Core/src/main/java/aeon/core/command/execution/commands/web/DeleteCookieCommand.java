package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Deletes a cookie with a given name
 */
public class DeleteCookieCommand extends Command {
    private String cookie;

    /**
     * Initializes a new instance of the DeleteCookieCommand class.
     * <p>
     * * @param log    The logger.
     *
     * @param cookie Name of the cookie to be deleted.
     */
    public DeleteCookieCommand(String cookie) {
        super(String.format(Locale.getDefault(), Resources.getString("DeleteCookieCommand_Info"), cookie));
        this.cookie = cookie;
    }

    /**
     * The method that provides the logic for the command
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver");
        }
        ((IWebDriver) driver).DeleteCookie(this.cookie);
    }
}
