package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * This class serves as a base for all grid row actions.
 *
 * @param <T> A sub class of RowActions. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 * @param <K> A sub class of RowElements. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *            an IBy as the second parameter.
 */
public abstract class RowActions<T extends RowActions, K extends RowElements> {

    IByWeb selector;
    AutomationInfo automationInfo;
    private Iterable<IByWeb> switchMechanism;
    private Class<K> rowElementsClass;
    private Class<T> rowActionsClass;
    protected String rowSelector = "tr";

    /**
     * Initializes a new instance of {@link RowActions} class.
     *
     * @param rowActionsClass A sub class of {@link RowActions}
     * @param rowElementsClass A sub class of {@link RowElements}
     */
    RowActions(Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    /**
     * Initializes a new instance of {@link RowActions} class.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowActionsClass A sub class of {@link RowActions}
     * @param rowElementsClass A sub class of {@link RowElements}
     */
    public RowActions(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.selector = selector;
        this.automationInfo = automationInfo;
        this.switchMechanism = switchMechanism;
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    /**
     * Sets the context. This method has to be called from the wrapping class after initialization.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public void setContext(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism){
        this.automationInfo = automationInfo;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
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
        String indexCssSelectorNthoftype = rowSelector + ":nth-of-type";
        IByWeb updatedSelector = selector.toJQuery().find(String.format("%1$s(%2$s)", indexCssSelectorNthoftype, index));

        return newInstanceOfK(updatedSelector);
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
    K newInstanceOfK(IByWeb updatedSelector) {
        Class classToLoad = rowElementsClass;
        Class[] cArgs = new Class[3];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IByWeb.class;
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
    T newInstanceOfT(IByWeb updatedSelector) {
        Class classToLoad = rowActionsClass;
        try {
            T instance = (T) classToLoad.getConstructor().newInstance();
            instance.setContext(automationInfo, updatedSelector, switchMechanism);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
