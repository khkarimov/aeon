package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;

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
