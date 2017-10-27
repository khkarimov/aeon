package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * The class to model tables.
 *
 * @param <T> the {@link TableActions} class for this table model.
 */
public abstract class Table<T extends TableActions> {

    public T rowBy;

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param rowBy The {@link TableActions} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IBy selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy The {@link TableActions} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
