package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
     * @param name The name of the cookie to get.
     */
    public GetCookieCommand(String name) {
        super(String.format(Locale.getDefault(), Resources.getString("GetCookieCommand_Info"), name));
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
        return ((IWebDriver) driver).GetCookie(name);
    }
}
