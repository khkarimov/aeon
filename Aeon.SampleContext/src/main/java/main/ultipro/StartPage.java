package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.TextBox;

/**
 * Created by SebastianR on 11/30/2016.
 */
public class StartPage {
    public TextBox ssnTextBox;
    public TextBox confirmSSNTextBox;
    public TextBox firstName;
    public TextBox lastName;
    public TextBox employeeNumber;


    public StartPage(AutomationInfo automationInfo){
        //Controls
        ssnTextBox = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbSSN"));
        confirmSSNTextBox = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbSSNConfirmed"));
        firstName = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbNameFirst"));
        lastName = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbNameLast"));
        employeeNumber = new TextBox(automationInfo, By.cssSelector("#ctl00_Content_StartFV_txbEmpNo"));
    }
}
