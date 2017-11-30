package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * The page containing the address of the currently logged in employee.
 */
public class PersonalPage extends Page {

    public TextBox addressLine;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public PersonalPage(AutomationInfo automationInfo){
        // Controls
        addressLine = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_NewHirePersonalFV_txbAddressLine1"));
    }
}
