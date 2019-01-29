package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model list groups.
 *
 * @param <T> the {@link ListGroupActions} class for this list group model.
 */
public class ListContainer<T extends ListGroupActions> {

    public T rowBy;

    /**
     * Creates a new instance of {@link ListContainer}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param rowBy          The {@link ListGroupActions} object to use for list group actions
     */
    public ListContainer(AutomationInfo automationInfo, IByWeb selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link ListContainer}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy           The {@link ListGroupActions} object to use for list group actions
     */
    public ListContainer(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
