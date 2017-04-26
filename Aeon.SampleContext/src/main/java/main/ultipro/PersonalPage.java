package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.TextBox;


/**
 * Created by SebastianR on 11/30/2016.
 */
public class PersonalPage {
    public TextBox addressLine;

    public PersonalPage(AutomationInfo automationInfo){
        // Controls
        addressLine = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_NewHirePersonalFV_txbAddressLine1"));

    }
}
