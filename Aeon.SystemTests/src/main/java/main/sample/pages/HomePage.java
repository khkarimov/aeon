package main.sample.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.models.Page;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage extends Page {

    public aeon.core.testabstraction.elements.web.Link viewPayStatement;

    public HomePage(AutomationInfo automationInfo) {
        viewPayStatement = new aeon.core.testabstraction.elements.web.Link(automationInfo, By.cssSelector("a[id*='viewPayStatementLink']"));
    }


}
