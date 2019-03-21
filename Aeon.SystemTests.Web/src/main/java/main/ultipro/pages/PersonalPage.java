package main.ultipro.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

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
    public PersonalPage(AutomationInfo automationInfo) {
        // Controls
        addressLine = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_NewHirePersonalFV_txbAddressLine1"));
    }
}
