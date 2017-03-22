package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created By AdamC on 4/13/2016.
 */
public abstract class RowElements extends WebElement {
    public RowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector);
    }
}
