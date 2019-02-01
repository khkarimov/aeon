package main.ultipro.myemployeesgrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Table;

/**
 * Model for the employees grid.
 */
public class MyEmployeesGrid extends Table<EmployeeTableContainer> {

    /**
     * Constructor.
     *
     * @param automationInfo   The automation info object to use.
     * @param selector         The selector that identifies the table.
     * @param switchMechanism  The switch mechanism for the web element.
     * @param employeesHeaders The corresponding Grid Headers object.
     */
    public MyEmployeesGrid(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, EmployeeTableContainer employeesHeaders) {
        super(automationInfo, selector, switchMechanism, employeesHeaders);
    }
}
