package com.ultimatesoftware.aeon.core.command.execution.commands.interfaces;

import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Finds a grid element.
 */
public interface IGridWebSelectorFinder extends IWebSelectorFinder {

    /**
     * Gets the JQuery expression to find a row within the table.
     *
     * @return JQuery Expression
     */
    ByJQuery getJQueryRowFinder();

    /**
     * Gets the index of a row utilizing a web driver.
     *
     * @param driver The facade for the framework abstraction layer.
     * @return The index of the row.
     */
    int getRowIndex(IWebDriver driver);
}
