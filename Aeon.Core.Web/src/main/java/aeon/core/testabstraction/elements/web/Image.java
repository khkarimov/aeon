package aeon.core.testabstraction.elements.web;

/**
 * Created by SebastianR on 6/3/2016.
 */

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Image class.
 */
public class Image extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private Iterable<IByWeb> switchMechanism;

    /**
     * Initializes a new instance of {@link Image} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public Image(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of {@link Image} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     * @param switchMechanism The switch mechanism for the web element.
     */
    public Image(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }
}
