package echo.core.command_execution.commands.interfaces;

import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;
import echo.core.framework_abstraction.IElement;

/**
 * Finds a web element.
 */
public interface IWebElementFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param parameterObject            Parameter Object.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    IElement FindElement(IWebDriver driver, ParameterObject parameterObject);
}
