package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.models.Page;

import java.util.ArrayList;

/**
 * The home page containing some menu items.
 */
public class HomePage extends Page {

    public Link viewPayStatement;
    public Button menuButton;
    public Button myTeam;
    public Button myEmployees;
    public Button homeButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public HomePage(AutomationInfo automationInfo) {
        viewPayStatement = new Link(automationInfo, By.cssSelector("a[id*='viewPayStatementLink']"));
        menuButton = new Button(automationInfo, By.cssSelector("#menuButtonContainer > div.menuButton"), new ArrayList<>()); //passing in an empty ArrayList sets the frame to the default frame
        myTeam = new Button(automationInfo, By.cssSelector("[data-uitoggle=\"menu_my_team\"]"));
        myEmployees = new Button(automationInfo, By.cssSelector("[data-id=\"167\"]"));
        homeButton = new Button(automationInfo, By.cssSelector("#link_home > div:nth-child(1)"));
    }
}
