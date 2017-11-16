package aeon.core.testabstraction.elements.mobile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;

public class NativeLabel extends NativeAppElement {
    public NativeLabel(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public NativeLabel(IBy selector) {
        super(selector);
    }

    public NativeLabel(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
    }
}
