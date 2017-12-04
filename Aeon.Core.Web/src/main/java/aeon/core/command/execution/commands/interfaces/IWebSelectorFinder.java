package aeon.core.command.execution.commands.interfaces;

import aeon.core.common.web.interfaces.IByWeb;
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
     * @return An {@link IByWeb}.
     */
    IByWeb findSelector(IWebDriver driver, IByWeb selector);
}
