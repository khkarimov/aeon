package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/31/2016.
 */

/**
 * Asserts that an element is enabled.
 */
public class EnabledCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the <see cref="EnabledCommand"/> class.
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
