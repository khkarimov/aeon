package echo.core.command_execution.commands.interfaces;

import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;

/**
 * Finds a web element.
 */
public interface IWebSelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    IBy FindSelector(IWebDriver driver, IBy selector);
}
