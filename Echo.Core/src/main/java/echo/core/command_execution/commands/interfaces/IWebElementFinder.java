package echo.core.command_execution.commands.interfaces;

import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IElement;

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
    IElement FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject);

    /**
     * Finds a web element utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    IElement FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector);
}
