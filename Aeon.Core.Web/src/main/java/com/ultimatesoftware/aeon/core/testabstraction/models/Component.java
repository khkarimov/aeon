package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.WebElement;

/**
 * Parent class for modeling a set of components within one item of a list group.
 */
public abstract class Component extends WebElement {

    /**
     * Constructor for abstract class RowElements.
     *
     * @param automationInfo  The automation info for the constructor.
     * @param selector        The chosen element.
     * @param switchMechanism The Iterable of IBy.
     */
    public Component(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Constructor for abstract class RowElements.
     *
     * @param automationInfo The automation info for the constructor.
     * @param selector       The chosen element.
     */
    public Component(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector, (IByWeb[]) null);
    }
}
