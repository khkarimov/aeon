package main;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.by;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public aeon.core.testabstraction.elements.web.Link ViewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        ViewPayStatement = new aeon.core.testabstraction.elements.web.Link(automationInfo, by.CssSelector("a[id*='viewPayStatementLink']"));
    }


}
