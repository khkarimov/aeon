package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * The class for Table Elements modeling.
 */
public abstract class TableElements extends RowElements {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info The info for the constructor.
     * @param selector The chosen element.
     * @param switchMechanism The Iterable of IBy.
     *
     */
    public TableElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
