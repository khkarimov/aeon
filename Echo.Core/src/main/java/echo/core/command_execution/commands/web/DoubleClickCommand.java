package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/26/2016.
 */

/**
 * Double clicks an element.
 */
public class DoubleClickCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the DoubleClickCommand.
     *
     * @param log         The logger.
     * @param selector    The selector.
     * @param initializer The web command initializer.
     */
    public DoubleClickCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DoubleClickCommand_Info"), selector), selector, initializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.DoubleClick(getGuid(), control);
    }
}
