package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for links.
 */
public class Link extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of {@link Link} class.
     *
     * @param info     The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public Link(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of {@link Link} class.
     *
     * @param info            The AutomationInfo.
     * @param selector        IBy selector that will identify the element
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Link(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }

    /**
     * Initializes a new instance pf {@link Link} class.
     *
     * @param selector IBy selector that will identify the element.
     */
    public Link(IByWeb selector) {
        this(null, selector);
    }
}
