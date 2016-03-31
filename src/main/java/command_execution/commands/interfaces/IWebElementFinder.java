package command_execution.commands.interfaces;

import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

/**
 * Finds a web element.
 */
public interface IWebElementFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param parameterObject            Parameter Object.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    WebElement FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Finds a web element utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    WebElement FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector);
}
