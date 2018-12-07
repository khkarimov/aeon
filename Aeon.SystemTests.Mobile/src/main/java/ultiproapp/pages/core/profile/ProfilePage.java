package ultiproapp.pages.core.profile;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.models.Page;

public class ProfilePage extends Page {

    private String parentPageSelector = "upa-person-hub:not([aria-hidden]) ";

    public Button profileBackButton;
    public Label profileName;
    public Label profileTitle;
    public Button editButton;

    public ProfilePage(AutomationInfo info) {

        profileBackButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        profileName = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"employee-full-name\"]"));
        profileTitle = new Label(info, By.cssSelector(parentPageSelector + "[data-automation=\"employee-job-title\"]"));
        editButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"profile-edit-button\"]"));

    }

}
