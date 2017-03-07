package aeon.core.common.web;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.web.WebControl;
import aeon.core.framework_abstraction.drivers.IWebDriver;

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
    Collection<WebControl> FindElements(IWebDriver driver, IBy selector);
}
