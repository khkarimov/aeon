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
    private By selector;
    private AutomationInfo automationInfo;
    private Class<K> rowElementsClass;
    private Class<T> rowActionsClass;

    public RowActions(AutomationInfo automationInfo, By selector, Class<T> rowActionsClass, Class<K> rowElementsClass) {
        this.selector = selector;
        this.automationInfo = automationInfo;
        this.rowElementsClass = rowElementsClass;
        this.rowActionsClass = rowActionsClass;
    }

    public K index(int index) {
        return findRowByIndex(index);
    }

    protected K findRowByIndex(int index) {
        //This is required when you are instantiating a generic object with parameters in the constructor
        By newBy = By.CssSelector(selector.toString() + " tbody tr:nth-of-type(" + index + ")"); // should look into using the ByJquery class instead of the By

        return newInstanceOfK(newBy);
    }

    public T findRow(String value, IBy columnHeaderSelector) {
        // Create a JQuery object and update By
        columnHeaderSelector.ToJQuery().add(String.format("tr > td:contains(%1$s)", value)).parent("tr");

        Class classToLoad = rowActionsClass;
        Class[] cArg = new Class[4];
        cArg[0] = AutomationInfo.class;
        cArg[2] = By.class;
        cArg[3] = rowActionsClass;
        cArg[4] = rowElementsClass;


        return (T) this;
    }

    public K getRow() {
        try {
            return rowElementsClass.newInstance();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private K newInstanceOfK(IBy updatedSelector){
        Class classToLoad = rowElementsClass;
        Class[] cArgs = new Class[2];
        cArgs[1] = AutomationInfo.class;
        cArgs[2] = IBy.class;

        try {
            return (K) classToLoad.getDeclaredConstructor(cArgs).newInstance(automationInfo, updatedSelector);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
