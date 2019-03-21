package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;

/**
 * Image class.
 */
public class Image extends WebElement {

    /**
     * Initializes a new instance of {@link Image} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Image(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Initializes a new instance of {@link Image} class.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Image(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }
}
