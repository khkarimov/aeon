package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model tables.
 *
 * @param <T> the {@link Table} class for this table model.
 */
public abstract class TableContainer<T extends Table> {

    public T rowBy;

    /**
     * Creates a new instance of {@link TableContainer}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param employeeTable  The {@link Table} object to use for table actions
     */
    public TableContainer(AutomationInfo automationInfo, IByWeb selector, T employeeTable) {
        this.rowBy = employeeTable;

        this.rowBy.setContext(automationInfo, selector, (IByWeb[]) null);
    }

    /**
     * Creates a new instance of {@link TableContainer}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param employeeTable   The {@link Table} object to use for table actions
     */
    public TableContainer(AutomationInfo automationInfo, IByWeb selector, T employeeTable, IByWeb... switchMechanism) {
        this.rowBy = employeeTable;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
