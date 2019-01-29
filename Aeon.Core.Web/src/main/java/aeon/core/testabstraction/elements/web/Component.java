package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Parent class for modeling one row of elements of a table.
 */
@Deprecated
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
