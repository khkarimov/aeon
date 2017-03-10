package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
     * @param name  The name of the cookie.
     * @param value The value.
     */
    public ModifyCookieCommand(String name, String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ModifyCookieCommand_Info"), name, value));
        this.name = name;
        this.value = value;
    }

    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).ModifyCookie(name, value);
    }
}
