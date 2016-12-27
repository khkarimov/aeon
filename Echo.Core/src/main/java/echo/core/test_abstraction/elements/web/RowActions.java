package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.common.web.selectors.ByJQuery;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by AdamC on 4/13/2016.
 */
public class RowActions<T extends RowActions, K extends RowElements> {
    private IBy selector;
    private AutomationInfo automationInfo;
    private Class<K> rowElementsClass;
    private Class<T> rowActionsClass;

    public RowActions(AutomationInfo automationInfo, IBy selector, Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.selector = selector;
        this.automationInfo = automationInfo;
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    public K index(int index) {
        return findRowByIndex(index);
    }

    protected K findRowByIndex(int index) {
        //By updatedSelector = By.CssSelector(String.format("%1$s tbody tr:nth-of-type(%2$s)", selector, index));//selector.toString() + " tbody tr:nth-of-type(" + index + ")"); // should look into using the ByJquery class instead of the By
        IBy updatedSelector = selector.ToJQuery().find(String.format("tbody tr:nth-of-type(%1$s)", index));
        return newInstanceOfK(updatedSelector);
    }

    public T findRow(String value) {
        // Create a JQuery object and update By
        IBy updatedSelector = selector.ToJQuery().find(String.format("tr > td:contains(%1$s)", value)).parent("tr");

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
        Class[] cArgs = new Class[2];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;

        try {
            return (K) classToLoad.getDeclaredConstructor(cArgs).newInstance(automationInfo, updatedSelector);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of T with a new selector. The new selector refers to the rows that contain the row elements defined in the K class.
     * @param updatedSelector The updated selector that references 1 or more rows.
     * @return Returns a new instance of T.
     */
    private T newInstanceOfT(IBy updatedSelector){
        Class classToLoad = rowActionsClass;
        Class[] cArgs = new Class[2];
        cArgs[0] = AutomationInfo.class;
        cArgs[1] = IBy.class;

        try{
            return (T) classToLoad.getConstructor(cArgs).newInstance(automationInfo, updatedSelector);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
