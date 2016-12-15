package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.TextBox;


/**
 * Created by SebastianR on 11/30/2016.
 */
public class PersonalPage {
    private AutomationInfo automationInfo;

    public TextBox addressLine;

    public PersonalPage(AutomationInfo automationInfo){
        this.automationInfo = automationInfo;
        // Controls
        addressLine = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_NewHirePersonalFV_txbAddressLine1"));

    }
}
