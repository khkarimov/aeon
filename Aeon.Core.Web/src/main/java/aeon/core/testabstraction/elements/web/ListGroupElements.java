package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Parent class for modeling a set of elements within one item of a list group.
 */
public abstract class ListGroupElements extends RowElements {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info The info for the constructor.
     * @param selector The chosen element.
     * @param switchMechanism The Iterable of IBy.
     *
     */
    public ListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
