package ultiproapp.pages.shared.teampages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.shared.teampages.myteamlistgroup.MyTeamListGroupActions;

public class MyTeamListPage extends Page {

    private String parentPageSelector = "upa-team-members:not([aria-hidden]) ";

    public Button backButton;
    public Label titleLabel;
    public ListGroup<MyTeamListGroupActions> teamMemberList;

    public MyTeamListPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"my-team-title\"]"));
        teamMemberList = new ListGroup<>(info, By.cssSelector(parentPageSelector + "[data-automation=\"team-member-list\"]"), new MyTeamListGroupActions());

    }

}
