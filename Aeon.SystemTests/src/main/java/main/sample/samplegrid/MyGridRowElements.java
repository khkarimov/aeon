package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.TableElements;

public class MyGridRowElements extends TableElements {
    public Button checkBoxButton;
    public Label unitPrice;

    public MyGridRowElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        checkBoxButton = new Button(info, selector.toJQuery().find("span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center"));
        unitPrice = new Label(info, selector.toJQuery().find("td:nth-child(4)"));
    }
}
