package aeon.core.testabstraction.elements.web;

/**
 * Created by SebastianR on 6/3/2016.
 */

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

public class Image extends WebElement {
    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    public Image(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public Image(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this. switchMechanism = switchMechanism;
    }
}
