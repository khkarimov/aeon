package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by Steve Foo on 6/29/2016.
 */
public class SelectedCommand extends WebControlCommand{

    /**
     * Initializes a new instance of the SelectedCommand.
     * @param log The logger.
     * @param selector The selector.
     * @param commandInitializer The command initializer.
     */
    public SelectedCommand(ILog log, IBy selector, ICommandInitializer commandInitializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("SelectedCommand_Info"), selector), selector, commandInitializer);
    }

    /**
     * Provides the logic for the command.
     * @param driver The web driver.
     * @param control The web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Selected(getGuid(), control);
    }
}
