package main.ultipro.my_employees_grid;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.web.Link;
import echo.core.test_abstraction.elements.web.RowElements;

/**
 * Created by SebastianR on 12/29/2016.
 */
public class MyEmployeesRowElements extends RowElements {
    public Link employeeLink;
    public MyEmployeesRowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        employeeLink = new Link(info, selector.ToJQuery().find("#ctl00_Content_GridView1_ctl02_clDetail"), switchMechanism);
    }
}
