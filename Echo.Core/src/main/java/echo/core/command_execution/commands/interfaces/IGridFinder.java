package echo.core.command_execution.commands.interfaces;

import echo.core.common.web.selectors.ByJQuery;
import echo.core.framework_abstraction.IWebDriver;

/**
 * Finds a grid web element.
 */
public interface IGridFinder extends IGridSelectorFinder {
    /**
     * Gets the jQuery expression to find a grid within the table.
     */
    ByJQuery getJQueryGridFinder();

    /**
     * Gets the index of a grid utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @return The index of the grid.
     */
    int GetGridIndex(IWebDriver driver);
}
