package echo.core.common;

import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;
import echo.core.framework_abstraction.WebElement;

import java.util.Collection;

/**
 * Finds a collection of web elements utilizing a web driver.
 */
public interface IWebElementsFinder {
    /**
     * Finds a collection of web elements utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    Collection<WebElement> FindElements(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector);
}
