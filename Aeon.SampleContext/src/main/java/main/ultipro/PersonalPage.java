package main.ultipro;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.TextBox;


/**
 * Created by SebastianR on 11/30/2016.
 */
public class PersonalPage {
    public TextBox addressLine;

    public PersonalPage(AutomationInfo automationInfo){
        // Controls
        addressLine = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_NewHirePersonalFV_txbAddressLine1"));

    }
}
