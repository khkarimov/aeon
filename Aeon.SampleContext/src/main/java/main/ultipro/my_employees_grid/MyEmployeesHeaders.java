package main.ultipro.my_employees_grid;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.RowActions;

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

    public MyEmployeesHeaders primaryWorkPhone(String value) {
        return findRow(value, By.CssSelector("#GridView1_colHeaders > th:nth-child(3)"));
    }
}
