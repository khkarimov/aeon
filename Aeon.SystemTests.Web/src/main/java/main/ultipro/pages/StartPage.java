package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * Start page of the New Hire Wizard.
 */
public class StartPage extends Page {

    public TextBox ssnTextBox;
    public TextBox confirmSSNTextBox;
    public TextBox firstName;
    public TextBox lastName;
    public TextBox employeeNumber;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    StartPage(AutomationInfo automationInfo) {
        //Controls
        ssnTextBox = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbSSN"));
        confirmSSNTextBox = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbSSNConfirmed"));
        firstName = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbNameFirst"));
        lastName = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbNameLast"));
        employeeNumber = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbEmpNo"));
    }
}
