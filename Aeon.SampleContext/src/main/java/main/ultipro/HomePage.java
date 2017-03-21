package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.by;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Link;

import java.util.ArrayList;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public Link ViewPayStatement;
    public Button menuButton;
    public Button myTeam;
    public Button myEmployees;
    public Button homeButton;

    public HomePage(AutomationInfo automationInfo) {
        ViewPayStatement = new Link(automationInfo, by.CssSelector("a[id*='viewPayStatementLink']"));
        menuButton = new Button(automationInfo, by.CssSelector("#menuButtonContainer > div.menuButton"), new ArrayList<>()); //passing in an empty ArrayList sets the frame to the default frame
        myTeam = new Button(automationInfo, by.CssSelector("div.menuTopHeader:nth-child(2)"));
        myEmployees = new Button(automationInfo, by.CssSelector("div.menuContentContainer:nth-child(3) > div:nth-child(1) > div:nth-child(2) > h3:nth-child(1)"));
        homeButton = new Button(automationInfo, by.CssSelector("#link_home > div:nth-child(1)"));
    }


}
