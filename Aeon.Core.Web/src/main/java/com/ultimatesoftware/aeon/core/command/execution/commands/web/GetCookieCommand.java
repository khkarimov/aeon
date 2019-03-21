package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Gets a specific cookie.
 */
public class GetCookieCommand extends CommandWithReturn {

    private String name;

    /**
     * Initializes a new instance of the {@link GetCookieCommand} class.
     *
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
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getCookie(name);
    }
}
