package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Returns all of the browsers cookies.
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
     *
     * @param driver The framework abstraction facade.
     * @return Returns a Collection of {@link IWebCookie}
     */
    @Override
    protected Object commandDelegate(IDriver driver) {
        return ((IWebDriver) driver).getAllCookies();
    }
}
