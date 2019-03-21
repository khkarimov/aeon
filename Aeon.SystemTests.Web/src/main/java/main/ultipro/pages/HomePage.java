package main.ultipro.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Link;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

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
        menuButton = new Button(automationInfo, By.cssSelector("#menuButtonContainer > div.menuButton"), new IByWeb[0]); //passing in a null switchMechanism sets the frame to the default frame
        myTeam = new Button(automationInfo, By.cssSelector("[data-uitoggle=\"menu_my_team\"]"));
        myEmployees = new Button(automationInfo, By.cssSelector("[data-id=\"167\"]"));
        homeButton = new Button(automationInfo, By.cssSelector("#link_home > div:nth-child(1)"));
    }
}
