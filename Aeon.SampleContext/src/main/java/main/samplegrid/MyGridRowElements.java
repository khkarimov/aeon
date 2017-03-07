package main.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.RowElements;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridRowElements extends RowElements {
    public Button checkBoxButton;

    public MyGridRowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        checkBoxButton = new Button(info, selector.ToJQuery().find("span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center"));
    }
}
