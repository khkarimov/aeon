package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.models.Page;

/**
 * The New Hire Wizard page.
 */
public class NewHireWizard extends Page {

    public PersonalPage personalPage;
    public StartPage startPage;
    public Button cancelButton;
    public Button nextButton;
    public Button backButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public NewHireWizard(AutomationInfo automationInfo){
        // Pages
        personalPage = new PersonalPage(automationInfo);
        startPage = new StartPage(automationInfo);

        // Controls
        nextButton = new Button(automationInfo, By.cssSelector("#ctl00_btnNext"));
        backButton = new Button(automationInfo, By.cssSelector("#ctl00_btnPrev"));
        cancelButton = new Button(automationInfo, By.cssSelector("#ctl00_btnCancel"));
    }
}
