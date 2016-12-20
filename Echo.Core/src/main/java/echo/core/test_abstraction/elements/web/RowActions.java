package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public class RowActions<T extends RowActions, K extends RowElements> {
    private final IBy selector;
    private final AutomationInfo automationInfo;
    private K row;

    public RowActions(AutomationInfo automationInfo, IBy selector) {
        this.selector = selector;
        this.automationInfo = automationInfo;
    }

    public K index(int index) {
        return findRowByIndex(index);
    }

    protected K findRowByIndex(int index) {
        return null;
    }

    protected T findRow(String value, IBy columnHeaderSelector) {

        return (T) this;
    }

    public K getRow() {
        return row;
    }

}
