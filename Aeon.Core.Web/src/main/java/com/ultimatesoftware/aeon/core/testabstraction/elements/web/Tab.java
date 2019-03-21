package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model Tab elements.
 */
public class Tab extends WebElement {

    /**
     * Creates a new instance of {@link Tab}.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Tab(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Creates a new instance of {@link Tab}.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Tab(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }
}
