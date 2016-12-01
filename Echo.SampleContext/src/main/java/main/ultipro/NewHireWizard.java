package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Button;

/**
 * Created by SebastianR on 11/23/2016.
 */
public class NewHireWizard {
    private AutomationInfo automationInfo;

    public PersonalPage personalPage;
    public StartPage startPage;
    public Button cancelButton;
    public Button nextButton;
    public Button backButton;

    public NewHireWizard(AutomationInfo automationInfo){
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(automationInfo);
        // Pages
        personalPage = new PersonalPage(automationInfo);
        startPage = new StartPage(automationInfo);

        // Controls
        nextButton = (Button) web.create(Button.class, "#ctl00_btnNext");
        backButton = (Button) web.create(Button.class, "#ctl00_btnPrev");
        cancelButton = (Button) web.create(Button.class, "#ctl00_btnCancel");
    }
}
