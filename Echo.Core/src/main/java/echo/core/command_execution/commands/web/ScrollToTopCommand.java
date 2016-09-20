package echo.core.command_execution.commands.web;
/**
 * Created by SebastianR on 5/26/2016.
 */

import echo.core.command_execution.commands.Command;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

/**
 * <p>Scrolls to the top of the page.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.ScrollToTop();</p>
 */

public class ScrollToTopCommand extends Command {
    /**
     * Initializes a new instance of the {@link ScrollToTopCommand} class.
     *
     * @param log The logger.
     */
    public ScrollToTopCommand(ILog log) {
        super(log, Resources.getString("ScrollToTopCommand_Info"));
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).ScrollToTop(getGuid());
    }
}
