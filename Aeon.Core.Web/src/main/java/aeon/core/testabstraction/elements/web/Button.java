package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * The class to model button elements.
 */
public class Button extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info     The automation info.
     * @param selector IBy selector that will identify the element.
     */
    public Button(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info            The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public Button(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
    }
}
