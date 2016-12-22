package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;

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
        Class classToLoad = rowElementsClass;
        Class[] cArg = new Class[2];
        cArg[0] = AutomationInfo.class;
        cArg[1] = By.class;

        By newBy = By.CssSelector(selector.toString() + " tbody tr:nth-of-type(" + index + ")");

        try {
            K object =  (K) classToLoad.getDeclaredConstructor(cArg).newInstance(automationInfo, newBy);
            return object;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected T findRow(String value, By columnHeaderSelector) {
        Class classToLoad = rowActionsClass;
        Class[] cArg = new Class[4];
        cArg[0] = AutomationInfo.class;
        cArg[2] = By.class;
        cArg[3] = rowActionsClass;
        cArg[4] = rowElementsClass;

        By newBy = By.CssSelector(selector.toString() + )
        return (T) this;
    }

    public K getRow() {
        try {
            return rowElementsClass.newInstance();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
