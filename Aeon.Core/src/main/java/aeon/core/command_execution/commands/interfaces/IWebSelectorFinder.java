package aeon.core.command_execution.commands.interfaces;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Finds a web element.
 */
public interface IWebSelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver   The facade for the framework abstraction layer.
     * @param selector Element locator.
     * @return An {@link IBy}.
     */
    IBy FindSelector(IWebDriver driver, IBy selector);
}
