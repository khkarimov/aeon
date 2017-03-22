package main.ultipro.my_employees_grid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.elements.web.RowElements;

/**
 * Created By SebastianR on 12/29/2016.
 */
public class MyEmployeesRowElements extends RowElements {
    public Link employeeLink;
    public MyEmployeesRowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        employeeLink = new Link(info, selector.toJQuery().find("#ctl00_Content_GridView1_ctl02_clDetail"), switchMechanism);
    }
}
