package main;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public aeon.core.test_abstraction.elements.web.Link ViewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        ViewPayStatement = new aeon.core.test_abstraction.elements.web.Link(automationInfo, By.CssSelector("a[id*='viewPayStatementLink']"));
    }


}
