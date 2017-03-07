package main.ultipro;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.Button;

/**
 * Created by SebastianR on 11/23/2016.
 */
public class NewHireWizard {
    public PersonalPage personalPage;
    public StartPage startPage;
    public Button cancelButton;
    public Button nextButton;
    public Button backButton;

    public NewHireWizard(AutomationInfo automationInfo){
        // Pages
        personalPage = new PersonalPage(automationInfo);
        startPage = new StartPage(automationInfo);

        // Controls
        nextButton = new Button(automationInfo, By.CssSelector("#ctl00_btnNext"));
        backButton = new Button(automationInfo, By.CssSelector("#ctl00_btnPrev"));
        cancelButton = new Button(automationInfo, By.CssSelector("#ctl00_btnCancel"));
    }
}
