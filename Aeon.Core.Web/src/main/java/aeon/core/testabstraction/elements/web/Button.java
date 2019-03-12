package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model button elements.
 */
public class Button extends WebElement {

    private AutomationInfo automationInfo;
    private IByWeb selector;

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Button(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
        this.automationInfo = automationInfo;
        this.selector = selector;
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Button(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
        this.automationInfo = automationInfo;
        this.selector = selector;
    }
}
