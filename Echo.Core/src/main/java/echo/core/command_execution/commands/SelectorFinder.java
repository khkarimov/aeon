package echo.core.command_execution.commands;

import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.webobjects.interfaces.IBy;

/**
 * Finds a web element.
 */
public class SelectorFinder implements ISelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return The narrowed down selector.
     */
    public IBy FindSelector(IFrameworkAbstractionFacade frameworkAbstractionFacade, IBy selector) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        return selector;
    }
}
