package main.samplegrid;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.RowActions;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridHeaders extends RowActions<MyGridHeaders, MyGridRowElements> {
    public MyGridHeaders(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism) {
        super(automationInfo, selector, switchMechanism, MyGridHeaders.class, MyGridRowElements.class);
    }

    public MyGridHeaders(AutomationInfo automationInfo, IBy selector){
        this(automationInfo, selector, null);
    }

    public MyGridHeaders material(String value) {
        return findRow(value, By.CssSelector("#grid-table-id th:contains(Material)"));
    }

    public MyGridHeaders quantity(String value){
        return findRow(value, By.CssSelector("#grid-table-id th:contains(Quantity)"));
    }

    public MyGridHeaders unitPrice(String value){
        return findRow(value, By.CssSelector("#grid-table-id th:contains(Unit price)"));
    }

}
