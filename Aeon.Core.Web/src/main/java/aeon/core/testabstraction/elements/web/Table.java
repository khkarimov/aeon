package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model tables.
 *
 * @param <T> the {@link ComponentTable} class for this table model.
 * @deprecated use ComponentLists instead
 */
@Deprecated
public abstract class Table<T extends ComponentTable> {

    public T rowBy;

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param rowBy          The {@link ComponentTable} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IByWeb selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link Table}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy           The {@link ComponentTable} object to use for table actions
     */
    public Table(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
