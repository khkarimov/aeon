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
public class NotExistsCommand extends WebControlCommand {

    public NotExistsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("CheckCommand_Info"), selector), selector, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        try {
            getCommandInitializer().FindElement(getGuid(), driver, control.getSelector());
        }
        //If element does not exist it should catch the exception and return successfully
        catch (NoSuchElementException e) {
            return;
        }
        driver.NotExists(getGuid(), control);
    }
}
