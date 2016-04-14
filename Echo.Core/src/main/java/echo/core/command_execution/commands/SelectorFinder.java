package echo.core.command_execution.commands;

import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;

/**
 * Finds a web element.
 */
public class SelectorFinder implements ISelectorFinder {
    /**
     * Finds a web element utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @param selector                   Element locator.
     * @return The narrowed down selector.
     */
    public IBy FindSelector(IDriver driver, IBy selector) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return selector;
    }
}
