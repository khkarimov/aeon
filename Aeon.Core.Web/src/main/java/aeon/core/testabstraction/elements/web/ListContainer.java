package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model list containers.
 *
 * @param <T> the {@link ComponentList} class for this list container model.
 */
public class ListContainer<T extends ComponentList> {

    public T rowBy;

    /**
     * Creates a new instance of {@link ListContainer}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param rowBy          The {@link ComponentList} object to use for list container actions
     */
    public ListContainer(AutomationInfo automationInfo, IByWeb selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, (IByWeb[]) null);
    }

    /**
     * Creates a new instance of {@link ListContainer}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy           The {@link ComponentList} object to use for list container actions
     */
    public ListContainer(AutomationInfo automationInfo, IByWeb selector, T rowBy, IByWeb... switchMechanism) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
