package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

public abstract class RowElements extends WebElement {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info The info for the constructor.
     * @param selector The chosen element.
     * @param switchMechanism The Iterable of IBy.
     *
     */
    public RowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector);
    }
}
