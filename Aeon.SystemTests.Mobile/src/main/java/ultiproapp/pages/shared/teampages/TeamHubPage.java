package ultiproapp.pages.shared.teampages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.shared.teampages.teamfeaturelistgroup.TeamFeatureListGroupActions;

public class TeamHubPage extends Page {

    private String parentPageSelector = "upa-team-page:not([aria-hidden]) ";

    public Button backButton;
    public Label titleLabel;
    public Button myTeamAvatarGroup;
    public ListGroup<TeamFeatureListGroupActions> featureList;

    public TeamHubPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"team-hub-title\"]"));
        myTeamAvatarGroup = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"my-team-avatar-group\"]"));
        featureList = new ListGroup<>(info, By.cssSelector(parentPageSelector + "[data-automation=\"team-feature-list\"]"), new TeamFeatureListGroupActions());

    }

}
