package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Created by AdamC on 4/13/2016.
 */

/**
 * This class serves as a base for all grid row actions.
 *
 * @param <T> A sub class of RowActions. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 * @param <K> A sub class of RowElements. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 */
public abstract class RowActions<T extends RowActions, K extends RowElements> {

    private IBy selector;
    private AutomationInfo automationInfo;
    private Iterable<IBy> switchMechanism;
    private Class<K> rowElementsClass;
    private Class<T> rowActionsClass;
    protected String indexCssSelectornthoftype = "tr:nth-of-type";
    protected String rowCssSelectornthoftype = "td:nth-of-type";
    protected String cssSelectorContains = "td:contains";
    protected String cssSelectorParents = "tr";

    /**
     * Initializes a new instance of {@link RowActions} class.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowActionsClass A sub class of {@link RowActions}
     * @param rowElementsClass A sub class of {@link RowElements}
     */
    public RowActions(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism, Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.selector = selector;
        this.automationInfo = automationInfo;
        this.switchMechanism = switchMechanism;
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    /**
     * Get the index of the referenced row that contains the row elements defined in the K class.
     *
     * @param index The index you are looking for.
     *
     * @return Returns an instance of K.
     */
    public K index(int index) {
        return findRowByIndex(index);
    }

    /**
     * Get a row by the index.
     *
     * @param index The index you are looking for.
     *
     * @return Returns an instance of K.
     */
    protected K findRowByIndex(int index) {
        IBy updatedSelector = selector.toJQuery().find(String.format("%1$s(%2$s)", indexCssSelectornthoftype, index));

        return newInstanceOfK(updatedSelector);
    }

    /**
     * Get a row by the value and column header.
     *
     * @param value The value you are looking for.
     * @param columnHeader THe header of the column.
     *
     * @return Returns an instance of T.
     */
    protected T findRow(String value, IBy columnHeader) {
        IBy updatedSelector = selector.toJQuery().find(String.format("%1$s(%2$s)", rowCssSelectornthoftype, getColumnIndex(columnHeader))).
                filter(String.format("%1$s(%2$s)", cssSelectorContains, value)).parents(String.format("%1$s", cssSelectorParents));

        return newInstanceOfT(updatedSelector);
    }


    /**
     * A function that returns the row.
     *
     * @return new instance of K selector.
     */
    public K getRow() {
        return newInstanceOfK(selector);
    }

    /**
     * Creates a new instance of K with a new selector. The new selector references the row containing the row elements defined in the K class.
     *
     * @param updatedSelector The updated selector that references only 1 row.
     * @return Returns an instance of K.
     */
    private K newInstanceOfK(IBy updatedSelector) {
        Class classToLoad = rowElementsClass;
        Class[] cArgs = new Class[3];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;
        cArgs[2] = Iterable.class;

        try {
            return (K) classToLoad.getDeclaredConstructor(cArgs).newInstance(automationInfo, updatedSelector, switchMechanism);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of T with a new selector. The new selector refers to the rows that contain the row elements defined in the K class.
     *
     * @return Returns a new instance of T.
     */
    private T newInstanceOfT(IBy updatedSelector) {
        Class classToLoad = rowActionsClass;
        Class[] cArgs = new Class[3];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;
        cArgs[2] = Iterable.class;
        try {
            return (T) classToLoad.getConstructor(cArgs).newInstance(automationInfo, updatedSelector, switchMechanism);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the column index based on the selector.
     *
     * @param columnSelector
     * @return the column index
     */
    private long getColumnIndex(IBy columnSelector) {
        return (long) ((IWebDriver) automationInfo.getDriver()).executeScript(String.format("var a=$(\"%1$s\").index();return a;", columnSelector)) + 1;
    }
}
