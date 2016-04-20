package echo.core.command_execution.commands.web;

import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IWebDriver;
import echo.core.framework_abstraction.WebControl;

/**
 * Clicks an element
 */
public class ClickCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected ClickCommand(ILog log, String message) {
        super(log, message);
    }

    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.Click(getGuid(), control);
    }
}
