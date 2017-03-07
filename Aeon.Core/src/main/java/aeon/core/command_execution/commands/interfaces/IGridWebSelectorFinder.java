package aeon.core.command_execution.commands.interfaces;

import aeon.core.common.web.selectors.ByJQuery;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Finds a grid element.
 */
public interface IGridWebSelectorFinder extends IWebSelectorFinder {
    /**
     * Gets the jquery expression to find a row within the table.
     * @return JQuery Expression
     */
    ByJQuery getJQueryRowFinder();

    /**
     * Gets the index of a row utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @return The index of the row.
     */
    int GetRowIndex(IWebDriver driver);
}
