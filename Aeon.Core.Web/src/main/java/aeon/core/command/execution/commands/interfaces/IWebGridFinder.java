package aeon.core.command.execution.commands.interfaces;

import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Finds a grid web element.
 */
public interface IWebGridFinder extends IGridWebSelectorFinder {

    /**
     * Gets the jquery expression to find a grid within the table.
     *
     * @return The {@link ByJQuery} Grid Finder expression
     */
    ByJQuery getJQueryGridFinder();

    /**
     * Gets the index of a grid utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @return The index of the grid.
     */
    int getGridIndex(IWebDriver driver);
}
