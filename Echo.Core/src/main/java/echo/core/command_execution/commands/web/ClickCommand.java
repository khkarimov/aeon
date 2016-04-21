package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.drivers.IWebDriver;
import echo.core.framework_abstraction.controls.web.WebControl;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     */
    public ClickCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, "Clicking", selector, initializer);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(getGuid(), control);
    }
}
