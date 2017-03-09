package aeon.core.command.execution.commands.web;
/**
 * Created by SebastianR on 5/26/2016.
 */

import aeon.core.command.execution.commands.Command;
import aeon.core.common.Resources;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * <p>Scrolls to the top of the page.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.browser.ScrollToTop();</p>
 */

public class ScrollToTopCommand extends Command {
    /**
     * Initializes a new instance of the {@link ScrollToTopCommand} class.
     */
    public ScrollToTopCommand() {
        super(Resources.getString("ScrollToTopCommand_Info"));
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
