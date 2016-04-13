package echo.core.command_execution.commands.interfaces;

import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

/**
 * Finds a web element.
 */
public interface ISelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return An <see cref="IWebElementAdapter"/>.
     */
    IBy FindSelector(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector);
}
