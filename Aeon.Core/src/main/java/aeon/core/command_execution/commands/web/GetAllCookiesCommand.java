package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.CommandWithReturn;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Created by RafaelT on 6/27/2016.
 */

/**
 * Returns all of the browsers cookies
 */
public class GetAllCookiesCommand extends CommandWithReturn {

    /**
     * Initializes a new instance of the {@link GetAllCookiesCommand} class.
     * @param log The logger.
     */
    public GetAllCookiesCommand(ILog log) {
        super(log, Resources.getString("GetAllCookiesCommand_Info"));
    }

    /**
     * Provides the logic for the command.
     * @param driver The framework abstraction facade.
     * @return Returns a Collection of {@link aeon.core.framework_abstraction.controls.web.IWebCookie}
     */
    @Override
    protected Object CommandDelegate(IDriver driver) {
        return ((IWebDriver) driver).GetAllCookies(getGuid());
    }
}
