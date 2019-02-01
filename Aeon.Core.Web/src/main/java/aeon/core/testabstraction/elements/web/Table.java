package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model tables.
 *
 * @param <T> the {@link EmployeeTable} class for this table model.
 * @deprecated use {@link ComponentList} instead
 */
@Deprecated
public abstract class Table<T extends EmployeeTable> {

    public T rowBy;

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param employeeTable  The {@link EmployeeTable} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IByWeb selector, T employeeTable) {
        this.rowBy = employeeTable;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param employeeTable   The {@link EmployeeTable} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, T employeeTable) {
        this.rowBy = employeeTable;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
