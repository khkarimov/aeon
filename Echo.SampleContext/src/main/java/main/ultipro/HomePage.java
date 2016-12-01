package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.Link;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Salvador Gandara on 5/27/2016.
 */
public class HomePage {
    public Link ViewPayStatement;
    public Button menuButon;
    public Button myTeam;
    public Button myEmployees;
    public Button homeButton;
    private AutomationInfo automationInfo;

    public HomePage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(this.automationInfo);
        ViewPayStatement = (Link) web.create(Link.class, "a[id*='viewPayStatementLink']");
        menuButon = (Button) web.create(Button.class, "#menuButtonContainer > div.menuButton", new ArrayList<>()); //passing in an empty ArrayList sets the frame to the default frame
        myTeam = (Button) web.create(Button.class, "div.menuTopHeader:nth-child(2)");
        myEmployees = (Button) web.create(Button.class, "div.menuContentContainer:nth-child(3) > div:nth-child(1) > div:nth-child(2) > h3:nth-child(1)");
        homeButton = (Button) web.create(Button.class, "#link_home > div:nth-child(1)");
    }


}
