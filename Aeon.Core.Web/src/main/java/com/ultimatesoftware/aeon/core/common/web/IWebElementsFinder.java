package com.ultimatesoftware.aeon.core.common.web;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Collection;

/**
 * Finds a collection of {@link WebControl} objects utilizing a web driver.
 */
public interface IWebElementsFinder {

    /**
     * Finds a collection of web elements utilizing a web driver.
     *
     * @param driver   The facade for the framework abstraction layer.
     * @param selector Element locator.
     * @return A collection of {@link WebControl} objects .
     */
    Collection<WebControl> findElements(IWebDriver driver, IByWeb selector);
}
