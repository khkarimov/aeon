package main.ultipro.my_employees_grid;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.RowActions;

/**
 * Created by SebastianR on 12/29/2016.
 */
public class MyEmployeesHeaders extends RowActions<MyEmployeesHeaders, MyEmployeesRowElements> {
    public MyEmployeesHeaders(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism) {
        super(automationInfo, selector, switchMechanism, MyEmployeesHeaders.class, MyEmployeesRowElements.class);
    }

    public MyEmployeesHeaders employeeNumber(String value){
        return findRow(value, By.CssSelector("#GridView1_colHeaders > th:nth-child(2)"));
    }
}
