package main;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.*;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public Link ViewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        ViewPayStatement = new Link(automationInfo, By.CssSelector("a[id*='viewPayStatementLink']"));
    }


}
