package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.exceptions.NoSuchElementException;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 5/31/2016.
 */

/**
 * Asserts that a certain element does not exist.
 */
public class NotExistsCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the NotExistsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     */
    public NotExistsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("CheckCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        try {
            getCommandInitializer().FindElement(getGuid(), driver, control.getSelector());
        } catch (NoSuchElementException e) {
            //If element does not exist it should catch the exception and return successfully
            return;
        }
        driver.NotExists(getGuid(), control);
    }
}
