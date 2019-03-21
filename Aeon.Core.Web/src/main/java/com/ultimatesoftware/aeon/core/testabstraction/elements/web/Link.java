package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Model class for links.
 */
public class Link extends WebElement {

    /**
     * Initializes a new instance of {@link Link} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Link(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
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
