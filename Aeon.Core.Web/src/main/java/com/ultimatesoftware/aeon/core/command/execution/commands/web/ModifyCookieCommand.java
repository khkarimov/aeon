package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.Command;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Modifies an existing cookie.
 */
public class ModifyCookieCommand extends Command {

    private String name;
    private String value;

    /**
     * Initializes a new instance of the {@link ModifyCookieCommand}.
     *
     * @param name  The name of the cookie.
     * @param value The value.
     */
    public ModifyCookieCommand(String name, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ModifyCookieCommand_Info"), name, value));
        this.name = name;
        this.value = value;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        ((IWebDriver) driver).modifyCookie(name, value);
    }
}
