package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.*;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    private AutomationInfo automationInfo;
    public Link ViewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        ViewPayStatement = new Link(automationInfo, By.CssSelector("a[id*='viewPayStatementLink']"));
    }


}
