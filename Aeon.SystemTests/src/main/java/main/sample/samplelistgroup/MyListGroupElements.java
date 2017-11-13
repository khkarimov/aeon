package main.sample.samplelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class MyListGroupElements extends ListGroupElements {
    public Label name;
    public Label description;

    public MyListGroupElements(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        name = new Label(info, selector.toJQuery().find("span.list-group-name"));
        description = new Label(info, selector.toJQuery().find("span.mdl-list__item-text-body"));
    }
}
