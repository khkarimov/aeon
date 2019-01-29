package aeon.core.testabstraction.elements.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.InlineJavaScript;

/**
 * This class serves as a base for all grid row actions.
 *
 * @param <T> A sub class of RowActions. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 * @param <K> A sub class of RowElements. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 */
public abstract class ListGroupActions<T extends ListGroupActions, K extends Component> extends RowActions<T, K> {

    /**
     * Initializes a new instance of {@link ListGroupActions} class.
     *
     * @param rowActionsClass  A sub class of {@link ListGroupActions}
     * @param rowElementsClass A sub class of {@link RowElements}
     */
    public ListGroupActions(Class<T> rowActionsClass, Class<K> rowElementsClass) {
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
     * <p>
     * The content of the HTML element has to contain the given string.
     *
     * @param value           The value you are looking for.
     * @param elementSelector The selector for the element based on which to find a list group.
     * @return Returns an instance of T.
     */
    protected T findRow(String value, String elementSelector) {
        IByWeb updatedSelector = selector.toJQuery()
                .find(String.format("%1s:contains(%2$s)", elementSelector, value))
                .parents(rowSelector);

        return newInstanceOfT(updatedSelector);
    }

    /**
     * Get a row by the value and column header.
     * <p>
     * The content of the HTML element has to exactly match the given value.
     *
     * @param value           The value you are looking for.
     * @param elementSelector The selector for the element based on which to find a list group.
     * @return Returns an instance of T.
     */
    protected T findRowByExactMatch(String value, String elementSelector) {
        IByWeb updatedSelector = selector.toJQuery()
                .find(elementSelector)
                .filter(new InlineJavaScript(String.format("function() {return $(this).text() == \"%1$s\";}", value)))
                .parents(rowSelector);

        return newInstanceOfT(updatedSelector);
    }
}
