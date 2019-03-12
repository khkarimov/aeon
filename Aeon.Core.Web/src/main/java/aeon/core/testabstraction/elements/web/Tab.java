package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model Tab elements.
 */
public class Tab extends WebElement {

    private AutomationInfo automationInfo;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Creates a new instance of {@link Tab}.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Tab(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
        this.automationInfo = automationInfo;
        this.selector = selector;
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
        this.automationInfo = automationInfo;
        this.selector = selector;
    }
}
