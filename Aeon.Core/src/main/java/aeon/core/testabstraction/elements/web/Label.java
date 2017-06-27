package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Label extends WebElement {

    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    /**
     * Initializes a new instance of {@link Label} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public Label(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of {@link Label} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Label(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }
}
