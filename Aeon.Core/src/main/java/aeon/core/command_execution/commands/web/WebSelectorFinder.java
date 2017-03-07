package aeon.core.command_execution.commands.web;

import aeon.core.command_execution.commands.interfaces.IWebSelectorFinder;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Finds a web element.
 */
public class WebSelectorFinder implements IWebSelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver   The facade for the framework abstraction layer.
     * @param selector Element locator.
     * @return The narrowed down selector.
     */
    public IBy FindSelector(IWebDriver driver, IBy selector) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return selector;
    }
}
