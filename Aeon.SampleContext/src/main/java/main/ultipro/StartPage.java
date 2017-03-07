package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.TextBox;

/**
 * Created by SebastianR on 11/30/2016.
 */
public class StartPage {
    public TextBox SSNTextBox;
    public TextBox confirmSSNTextBox;
    public TextBox firstName;
    public TextBox lastName;
    public TextBox employeeNumber;


    public StartPage(AutomationInfo automationInfo){
        //Controls
        SSNTextBox = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_StartFV_txbSSN"));
        confirmSSNTextBox = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_StartFV_txbSSNConfirmed"));
        firstName = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_StartFV_txbNameFirst"));
        lastName = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_StartFV_txbNameLast"));
        employeeNumber = new TextBox(automationInfo, By.CssSelector("#ctl00_Content_StartFV_txbEmpNo"));
    }
}
