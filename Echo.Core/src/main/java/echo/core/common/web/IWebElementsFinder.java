package echo.core.common.web;

import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;
import echo.core.framework_abstraction.IElement;

import java.util.Collection;

/**
 * Finds a collection of web elements utilizing a web driver.
 */
public interface IWebElementsFinder {
    /**
     * Finds a collection of web elements utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    Collection<IElement> FindElements(IWebDriver driver, IBy selector);
}
