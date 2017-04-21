package main;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public aeon.core.testabstraction.elements.web.Link viewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        viewPayStatement = new aeon.core.testabstraction.elements.web.Link(automationInfo, By.cssSelector("a[id*='viewPayStatementLink']"));
    }


}
