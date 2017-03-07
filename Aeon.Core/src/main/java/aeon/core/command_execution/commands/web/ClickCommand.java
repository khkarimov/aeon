package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {
    /**
     * Initializes a new instance of the {@link WebControlCommand} class.
     * @param log The logger.
     * @param selector The selector.
     * @param initializer The command initializer.
     */
    public ClickCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, String.format(Locale.getDefault(), Resources.getString("ClickCommand_Info"), selector), selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(getGuid(), control);
    }
}
