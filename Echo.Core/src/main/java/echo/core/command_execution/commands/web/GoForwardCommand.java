package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Move forward a single entry in the browser's history.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.GoForward();</p>
 * <p>
 * Does nothing if we are on the latest page viewed.
 */
public class GoForwardCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="GoForwardCommand"/> class.
     *
     * @param log The logger.
     */
    public GoForwardCommand(ILog log) {
        super(log, Resources.getString("GoForwardCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        ((IWebDriver) driver).GoForward(getGuid());
    }
}
