package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.TextBox;


/**
 * Created by SebastianR on 11/30/2016.
 */
public class PersonalPage {
    private AutomationInfo automationInfo;

    public TextBox addressLine;

    public PersonalPage(AutomationInfo automationInfo){
        this.automationInfo = automationInfo;
        WebFactory webFactory = new WebFactory(automationInfo);

        // Controls
        addressLine = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_NewHirePersonalFV_txbAddressLine1");

    }
}
