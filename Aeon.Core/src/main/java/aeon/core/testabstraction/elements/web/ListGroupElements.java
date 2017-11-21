package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * The class for List group elements modeling.
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
    public ListGroupElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
