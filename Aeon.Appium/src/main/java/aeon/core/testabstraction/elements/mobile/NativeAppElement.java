package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.WebElement;

/**
 * Phone class.
 */
public class NativeAppElement extends WebElement {
    public NativeAppElement(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public NativeAppElement(IBy selector) {
        super(selector);
    }

    public NativeAppElement(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
