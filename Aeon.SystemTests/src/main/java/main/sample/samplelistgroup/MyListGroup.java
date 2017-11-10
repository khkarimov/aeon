package main.sample.samplelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.ListGroup;

public class MyListGroup extends ListGroup<MyListGroupActions> {
    public MyListGroup(AutomationInfo automationInfo, IBy selector, MyListGroupActions gridHeaders) {
        super(automationInfo, selector, gridHeaders);
    }
}
