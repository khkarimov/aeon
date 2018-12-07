package ultiproapp.modals;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.models.Page;

public class WhatIsCompanyAccessCodeModal extends Page {

    public Label title;
    public Button closeButton;
    public Label whatIsAccessCodeHeading;
    public Label whatIsAccessCodeDescription;
    public Label obtainMyAccessCodeHeading;
    public Label obtainMyAccessCodeDescription;
    public Label clearASavedAccessCodeHeading;
    public Label clearASavedAccessCodeDescription;


    public WhatIsCompanyAccessCodeModal(AutomationInfo info) {

        title = new Label(info, By.dataAutomationAttribute("what-is-company-access-code-title"));
        closeButton = new Button(info, By.dataAutomationAttribute("close-button"));
        whatIsAccessCodeHeading = new Label(info, By.cssSelector("h2[data-automation=\"what-is-company-access-code\"]"));
        whatIsAccessCodeDescription = new Label(info, By.cssSelector("p[data-automation=\"what-is-company-access-code\"]"));
        obtainMyAccessCodeHeading = new Label(info, By.cssSelector("h2[data-automation=\"dont-know-access-code\"]"));
        obtainMyAccessCodeDescription = new Label(info, By.cssSelector("p[data-automation=\"dont-know-access-code\"]"));
        clearASavedAccessCodeHeading = new Label(info, By.cssSelector("h2[data-automation=\"clear-access-code\"]"));
        clearASavedAccessCodeDescription = new Label(info, By.cssSelector("p[data-automation=\"clear-access-code\"]"));

    }

}
