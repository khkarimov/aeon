package ultiproapp.pages.shared;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.models.Page;

public class HomePage extends Page {

    public Button profileButton;
    public Label fullNameLabel;
    public Label jobTitleLabel;
    public Label contactHRLabel;
    public Button payButton;
    public Button timeOffButton;
    public Button directoryButton;
    public Button goalsButton;
    public Button competenciesButton;
    public Button notesButton;
    public Button settingsButton;

    public HomePage(AutomationInfo info) {

        profileButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"person-avatar-button\"]"));
        fullNameLabel = new Label(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"person-avatar-name\"]"));
        jobTitleLabel = new Label(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"person-avatar-job-title\"]"));

        // TODO: Need to update the selector for Contact HR Label.
        contactHRLabel = new Label(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"person-avatar-job-title\"]"));

        // QuickLinks [Note: Possible to model this as ListGroup but when user logs in with a different language it will break.]
        payButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"mobile.home.pay\"]"));
        timeOffButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"mobile.home.timeOff\"]"));
        directoryButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"ultipro.Directory\"]"));
        goalsButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"ultipro.Goals\"]"));
        competenciesButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"ultipro.Competencies\"]"));
        notesButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"ultipro.Notes\"]"));
        settingsButton = new Button(info, By.cssSelector("upa-home-page:not([hidden]) [data-automation=\"ultipro.Settings\"]"));

    }

}
