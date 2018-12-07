package ultiproapp.pages.shared.personhubpage;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Image;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.shared.personhubpage.managerfeaturelistgroup.ManagerFeatureListGroupActions;

public class PersonHubPage extends Page {

    private String parentPageSelector = "upa-person-hub:not([aria-hidden]) ";

    public Button backButton;
    public Button moreButton;
    public Image avatar;
    public Label fullNameLabel;
    public Label jobTitleLabel;
    public ListGroup<ManagerFeatureListGroupActions> featureList;

    public PersonHubPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        moreButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"more-button\"] ion-button"));
        avatar = new Image(info, By.cssSelector(parentPageSelector + "[data-automation=\"employee-avatar\"]"));
        fullNameLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"employee-full-name\"]"));
        jobTitleLabel = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"employee-job-title\"]"));
        featureList = new ListGroup<>(info, By.cssSelector(parentPageSelector + "[data-automation=\"manager-feature-list\"]"), new ManagerFeatureListGroupActions());

    }

}
