package main.ultipro;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.Button;
import aeon.core.test_abstraction.elements.web.Link;

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
        ViewPayStatement = new Link(automationInfo, By.CssSelector("a[id*='viewPayStatementLink']"));
        menuButton = new Button(automationInfo, By.CssSelector("#menuButtonContainer > div.menuButton"), new ArrayList<>()); //passing in an empty ArrayList sets the frame to the default frame
        myTeam = new Button(automationInfo, By.CssSelector("div.menuTopHeader:nth-child(2)"));
        myEmployees = new Button(automationInfo, By.CssSelector("div.menuContentContainer:nth-child(3) > div:nth-child(1) > div:nth-child(2) > h3:nth-child(1)"));
        homeButton = new Button(automationInfo, By.CssSelector("#link_home > div:nth-child(1)"));
    }


}
