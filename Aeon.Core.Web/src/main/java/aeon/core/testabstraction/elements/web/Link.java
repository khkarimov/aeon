package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for links.
 */
public class Link extends WebElement {

    private AutomationInfo automationInfo;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of {@link Link} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Link(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
        this.automationInfo = automationInfo;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of {@link Link} class.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Link(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
        this.automationInfo = automationInfo;
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
