package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.by;
import aeon.core.testabstraction.elements.web.Button;

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
        nextButton = new Button(automationInfo, by.CssSelector("#ctl00_btnNext"));
        backButton = new Button(automationInfo, by.CssSelector("#ctl00_btnPrev"));
        cancelButton = new Button(automationInfo, by.CssSelector("#ctl00_btnCancel"));
    }
}
