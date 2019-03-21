package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.interfaces.IWebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

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
    public IByWeb findSelector(IWebDriver driver, IByWeb selector) {
        return selector;
    }
}
