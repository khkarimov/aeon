package aeon.core.command.execution.commands.web;

import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 6/27/2016.
 */

/**
 * Returns all of the browsers cookies
 */
public class GetAllCookiesCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetAllCookiesCommand} class.
     */
    public GetAllCookiesCommand() {
        super(Resources.getString("GetAllCookiesCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     * @param driver The framework abstraction facade.
     * @return Returns a Collection of {@link IWebCookie}
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getAllCookies();
    }
}
