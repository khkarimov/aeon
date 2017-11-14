package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * The class to model list groups.
 *
 * @param <T> the {@link ListGroupActions} class for this list group model.
 */
public class ListGroup<T extends ListGroupActions> {

    public T rowBy;

    /**
     * Creates a new instance of {@link ListGroup}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param rowBy The {@link ListGroupActions} object to use for list group actions
     */
    public ListGroup(AutomationInfo automationInfo, IBy selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link ListGroup}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy The {@link ListGroupActions} object to use for list group actions
     */
    public ListGroup(AutomationInfo automationInfo, IBy selector, Iterable<IBy> switchMechanism, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
