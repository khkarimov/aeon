package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Image class.
 */
public class Image extends WebElement {

    private AutomationInfo automationInfo;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Initializes a new instance of {@link Image} class.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public Image(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
        this.automationInfo = automationInfo;
        this.selector = selector;
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
        this.automationInfo = automationInfo;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }
}
