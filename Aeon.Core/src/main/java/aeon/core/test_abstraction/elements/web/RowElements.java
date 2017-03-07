package aeon.core.test_abstraction.elements.web;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public abstract class RowElements extends WebElement {
    public RowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector);
    }
}
