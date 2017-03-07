package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/31/2016.
 */

/**
 * Asserts that an element is enabled.
 */
public class EnabledCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public EnabledCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("EnabledCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * The method which provides the logic for the web element command.
     *
     * @param driver  The web driver.
     * @param control The web element.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.IsElementEnabled(getGuid(), control);
    }
}
