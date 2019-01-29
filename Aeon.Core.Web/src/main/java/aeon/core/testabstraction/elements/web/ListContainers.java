package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model list groups.
 *
 * @param <T> the {@link ComponentList} class for this list group model.
 */
public class ListContainers<T extends ComponentList> {

    public T rowBy;

    /**
     * Creates a new instance of {@link ListContainers}.
     *
     * @param automationInfo The AutomationInfo.
     * @param selector       IBy selector that will identify the element.
     * @param rowBy          The {@link ComponentList} object to use for list group actions
     */
    public ListContainers(AutomationInfo automationInfo, IByWeb selector, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, null);
    }

    /**
     * Creates a new instance of {@link ListContainers}.
     *
     * @param automationInfo  The AutomationInfo.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     * @param rowBy           The {@link ComponentList} object to use for list group actions
     */
    public ListContainers(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism, T rowBy) {
        this.rowBy = rowBy;

        this.rowBy.setContext(automationInfo, selector, switchMechanism);
    }
}
