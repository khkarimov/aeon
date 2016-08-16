package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Link;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public Link ViewPayStatement;
    private AutomationInfo automationInfo;

    public HomePage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(this.automationInfo);
        ViewPayStatement = (Link) web.create(Link.class, "a[id*='viewPayStatementLink']");
    }


}
