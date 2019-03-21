package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Deletes a cookie with a given name.
 */
public class DeleteCookieCommand extends Command {

    private String cookie;

    /**
     * Initializes a new instance of the DeleteCookieCommand class.
     *
     * @param cookie Name of the cookie to be deleted.
     */
    public DeleteCookieCommand(String cookie) {
        super(String.format(Locale.getDefault(), Resources.getString("DeleteCookieCommand_Info"), cookie));
        this.cookie = cookie;
    }

    /**
     * The method that provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).deleteCookie(this.cookie);
    }
}
