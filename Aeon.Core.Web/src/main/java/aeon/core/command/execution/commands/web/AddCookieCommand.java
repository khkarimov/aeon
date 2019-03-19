package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Returns the name value pair for a cookie.
 */
public class AddCookieCommand extends Command {

    private IWebCookie cookie;

    /**
     * Initializes a new instance of the {@link AddCookieCommand} class.
     *
     * @param cookie Cookie to be added.
     */
    public AddCookieCommand(IWebCookie cookie) {
        super(String.format(Locale.getDefault(), Resources.getString("AddCookieCommand_Info"), cookie.getName()));
        this.cookie = cookie;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).addCookie(cookie);
    }
}
