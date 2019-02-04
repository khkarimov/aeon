package main.ultipro.myemployeesgrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.TableContainer;

/**
 * Model for the employees grid.
 */
public class EmployeeTableContainer extends TableContainer<EmployeeTable> {

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies the table.
     * @param switchMechanism The switch mechanism for the web element.
     * @param employeeTable   The corresponding EmployeeTable object.
     */
    public EmployeeTableContainer(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, EmployeeTable employeeTable) {
        super(automationInfo, selector, switchMechanism, employeeTable);
    }
}
