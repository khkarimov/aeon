package command_execution.commands.interfaces;

import common.webobjects.ByJQuery;
import framework_abstraction.IFrameworkAbstractionFacade;

/**
 * Finds a grid element.
 */
public interface IGridSelectorFinder extends ISelectorFinder {
    /**
     * Gets the jQuery expression to find a row within the table.
     */
    ByJQuery getJQueryRowFinder();

    /**
     * Gets the index of a row utilizing a web driver.
     *
     * @param frameworkAbstractionFacade The facade for the framework abstraction layer.
     * @return The index of the row.
     */
    int GetRowIndex(IFrameworkAbstractionFacade frameworkAbstractionFacade);
}
