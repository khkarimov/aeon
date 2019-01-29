package aeon.core.testabstraction.elements.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * This class serves as a base for all grid row components.
 *
 * @param <T> A sub class of RowActions. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 * @param <K> A sub class of RowElements. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 */
@Deprecated
public abstract class ComponentTable<T extends ComponentTable, K extends RowElements> extends RowActions<T, K> {

    protected String cellSelector = "td";

    /**
     * Initializes a new instance of {@link ComponentTable} class.
     *
     * @param rowActionsClass  A sub class of {@link ComponentTable}
     * @param rowElementsClass A sub class of {@link RowElements}
     */
    public ComponentTable(Class<T> rowActionsClass, Class<K> rowElementsClass) {
        super(rowActionsClass, rowElementsClass);
    }

    /**
     * Get the index of the referenced row that contains the row elements defined in the K class.
     *
     * @param index The index you are looking for.
     * @return Returns an instance of K.
     */
    public K index(int index) {
        return findRowByIndex(index);
    }

    /**
     * Get a row by the index.
     *
     * @param index The index you are looking for.
     * @return Returns an instance of K.
     */
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
