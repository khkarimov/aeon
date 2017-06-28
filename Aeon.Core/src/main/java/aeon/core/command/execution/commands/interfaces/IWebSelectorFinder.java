package aeon.core.command.execution.commands.interfaces;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Interface for web element selector finder.
 */
public interface IWebSelectorFinder {

    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver   The facade for the framework abstraction layer.
     * @param selector Element locator.
     * @return An {@link IBy}.
     */
    IBy findSelector(IWebDriver driver, IBy selector);
}
