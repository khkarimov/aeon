package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Sets a username & password to a certain credential input.
 */
public class SetAuthenticationCommand extends Command{
    private String username;
    private String password;

    /**
     * Initializes a new instance of the {@link SetAuthenticationCommand} class.
     */
    public SetAuthenticationCommand(String username, String password) {
        super(String.format(Resources.getString("SetAuthenticationCommand_Info"), username, password));
        this.username = username;
        this.password = password;
    }

    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).setAuthenticationCredentials(username, password);
    }

}
