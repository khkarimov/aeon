package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.testabstraction.elements.web.Table;

public class MyGrid extends Table<MyGridHeaders> {
    public MyGrid(AutomationInfo automationInfo, IBy selector, MyGridHeaders gridHeaders) {
        super(automationInfo, selector, gridHeaders);
    }
}
