package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.ClientRects;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */

/**
 * Gets the bounding rectangle for the element.
 */
public class GetClientRectsCommand extends WebControlCommandWithReturn {

    /**
     * Initializes a new instance of the GetClientRectsCommand.
     *
     * @param log         The logger.
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public GetClientRectsCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("GetClientRectsCommand_Info"), selector), selector, initializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The WebDriver.
     * @param control The element.
     * @return A ClientRect with the bounding sides of the element.
     */
    @Override
    protected Object CommandDelegateOverride(IDriver driver, WebControl control) {
        return (ClientRects) ((IWebDriver) driver).GetClientRects(getGuid(), control);
    }
}
