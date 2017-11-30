package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;

/**
 * Created by Administrator on 6/17/2016.
 */
public class ListItem extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private Iterable<IByWeb> switchMechanism;

    /**
     * Initializes a new instance of {@link ListItem} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     */
    public ListItem(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Initializes a new instance of {@link ListItem} class.
     *
     * @param info The AutomationInfo.
     * @param selector IBy selector that will identify the element
     * @param switchMechanism The switch mechanism for the web element.
     */
    public ListItem(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }
}
