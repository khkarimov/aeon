package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.WebElement;

/**
 * Parent class for modeling a set of components within one item of a list group.
 */
public abstract class Component extends WebElement {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info            The info for the constructor.
     * @param selector        The chosen element.
     * @param switchMechanism The Iterable of IBy.
     */
    public Component(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
    }

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info     The info for the constructor.
     * @param selector The chosen element.
     */
    public Component(AutomationInfo info, IByWeb selector) {
        super(info, selector, new IByWeb[0]);
    }
}
