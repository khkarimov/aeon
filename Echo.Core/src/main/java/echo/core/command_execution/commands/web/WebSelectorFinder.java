package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.interfaces.IWebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;

/**
 * Finds a web element.
 */
public class WebSelectorFinder implements IWebSelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return The narrowed down selector.
     */
    public IBy FindSelector(IWebDriver driver, IBy selector) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return selector;
    }
}
