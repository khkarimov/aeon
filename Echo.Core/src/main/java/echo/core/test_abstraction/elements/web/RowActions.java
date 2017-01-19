package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.UUID;

/**
 * Created by AdamC on 4/13/2016.
 */

/**
 * This class serves as a base for all grid row actions.
 * @param <T> A sub class of RowActions. T must have a constructor that accepts an AutomationInfo object as the first parameter and
 *           an IBy as the second parameter.
 * @param <K> A sub class of RowElements. K must have a constructor that accepts an AutomationInfo object as the first parameter and
 *           an IBy as the second parameter.
 */
public abstract class RowActions<T extends RowActions, K extends RowElements> {
    private IBy selector;
    private AutomationInfo automationInfo;
    private Iterable<IBy> switchMechanism;
    private Class<K> rowElementsClass;
    private Class<T> rowActionsClass;

    public RowActions(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism, Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.selector = selector;
        this.automationInfo = automationInfo;
        this.switchMechanism = switchMechanism;
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    public K index(int index) {
        return findRowByIndex(index);
    }

    protected K findRowByIndex(int index) {
        IBy updatedSelector = selector.ToJQuery().find(String.format("tr:nth-of-type(%1$s)", index));

        return newInstanceOfK(updatedSelector);
    }

    protected T findRow(String value, IBy columnHeader) {
        IBy updatedSelector = selector.ToJQuery().find(String.format("td:nth-of-type(%1$s)", getColumnIndex(columnHeader))).filter(String.format("td:contains(%1$s)", value)).parents("tr");

        return newInstanceOfT(updatedSelector);
    }

    public K getRow() {
        return newInstanceOfK(selector);
    }

    /**
     * Creates a new instance of K with a new selector. The new selector references the row containing the row elements defined in the K class.
     * @param updatedSelector The updated selector that references only 1 row.
     * @return Returns an instance of K.
     */
    private K newInstanceOfK(IBy updatedSelector){
        Class classToLoad = rowElementsClass;
        Class[] cArgs = new Class[3];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;
        cArgs[2] = Iterable.class;

        try {
            return (K) classToLoad.getDeclaredConstructor(cArgs).newInstance(automationInfo, updatedSelector, switchMechanism);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of T with a new selector. The new selector refers to the rows that contain the row elements defined in the K class.
     * @return Returns a new instance of T.
     */
    private T newInstanceOfT(IBy updatedSelector){
        Class classToLoad = rowActionsClass;
        Class[] cArgs = new Class[3];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;
        cArgs[2] = Iterable.class;
        try{
            return (T) classToLoad.getConstructor(cArgs).newInstance(automationInfo, updatedSelector, switchMechanism);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private long getColumnIndex(IBy columnSelector){
        return (long)((IWebDriver)automationInfo.getDriver()).ExecuteScript(UUID.randomUUID(), getScript(columnSelector)) + 1;
    }

    /**
     * This function is used to determine the column position of the column header.
     * @param columnSelector A unique selector for the column header;
     * @return The jQuery script that determines the zero-based column index.
     */
    private String getScript(IBy columnSelector){
        return String.format("var a=$(\"%1$s\").index();return a;", columnSelector);
    }
}
