package common;

import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

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
