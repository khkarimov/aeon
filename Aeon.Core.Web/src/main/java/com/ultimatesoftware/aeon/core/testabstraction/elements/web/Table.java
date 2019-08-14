package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import com.ultimatesoftware.aeon.core.testabstraction.models.Component;

/**
 * This class serves as a base for all classes that define a component table.
 *
 * @param <T> A sub class of Table. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 * @param <K> A sub class of Component. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 */
public abstract class Table<T extends Table, K extends Component> extends RowActions<T, K> {

    protected String cellSelector = "td";

    /**
     * Initializes a new instance of {@link Table} class.
     *
     * @param tableClass     A sub class of {@link Table}
     * @param componentClass A sub class of {@link Component}
     */
    public Table(Class<T> tableClass, Class<K> componentClass) {
        super(tableClass, componentClass);
    }

    /**
     * Get the index of the referenced row that contains the row elements defined in the K class.
     *
     * @param index The index you are looking for.
     * @return Returns an instance of K.
     */
    @Override
    public K index(int index) {
        return findRowByIndex(index);
    }

    /**
     * Get a row by the index.
     *
     * @param index The index you are looking for.
     * @return Returns an instance of K.
     */
    @Override
    protected K findRowByIndex(int index) {
        IByWeb updatedSelector = selector.toJQuery().find(String.format("%1$s:nth-child(%2$d)", rowSelector, index));

        return newInstanceOfK(updatedSelector);
    }

    /**
     * Get a row by the value and column header.
     *
     * @param value        The value you are looking for.
     * @param columnHeader The header of the column.
     * @return Returns an instance of T.
     */
    protected T findRow(String value, IByWeb columnHeader) {
        IByWeb updatedSelector = selector.toJQuery()
                .find(String.format("%1$s:nth-child(%2$s)", cellSelector, getColumnIndex(columnHeader)))
                .filter(String.format("%1$s:contains(%2$s)", cellSelector, value))
                .parents(String.format("%1$s", rowSelector));

        return newInstanceOfT(updatedSelector);
    }

    /**
     * Gets the column index based on the selector.
     *
     * @param columnSelector Selector for the column.
     * @return the column index
     */
    private long getColumnIndex(IByWeb columnSelector) {
        return (long) ((IWebDriver) automationInfo.getDriver()).executeScript(String.format("var a=%1$s.index();return a;", columnSelector.toJQuery())) + 1;
    }
}
