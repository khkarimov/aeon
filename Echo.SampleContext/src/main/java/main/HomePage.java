package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.*;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    private AutomationInfo automationInfo;
    public Link ViewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
		WebFactory web = new WebFactory(this.automationInfo);
        ViewPayStatement = web.Link("a[id*='viewPayStatementLink']");
    }


}
