package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model Tab elements.
 */
public class Tab extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private IByWeb[] switchMechanism;

    /**
     * Creates a new instance of {@link Tab}.
     *
     * @param info     The automation info.
     * @param selector IBy selector that will identify the element.
     */
    public Tab(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Creates a new instance of {@link Tab}.
     *
     * @param info            The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Tab(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
    }
}
