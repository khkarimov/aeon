package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Parent class for modeling elements within one item of a list group.
 * <p>
 * Should not be inherited directly, please use {@link aeon.core.testabstraction.models.Component}
 */
public abstract class RowElements extends WebElement {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param info            The info for the constructor.
     * @param selector        The chosen element.
     * @param switchMechanism The Iterable of IBy.
     */
    public RowElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector);
    }
}
