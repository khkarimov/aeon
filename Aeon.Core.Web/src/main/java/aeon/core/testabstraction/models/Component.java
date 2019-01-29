package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.RowElements;

/**
 * Parent class for modeling a set of elements within one item of a list group.
 */
public abstract class Component extends RowElements {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info            The info for the constructor.
     * @param selector        The chosen element.
     * @param switchMechanism The Iterable of IBy.
     */
    public Component(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
