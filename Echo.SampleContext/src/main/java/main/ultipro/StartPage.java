package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.TextBox;

/**
 * Created by SebastianR on 11/30/2016.
 */
public class StartPage {
    private AutomationInfo automationInfo;

    public TextBox SSNTextBox;
    public TextBox confirmSSNTextBox;
    public TextBox firstName;
    public TextBox lastName;
    public TextBox employeeNumber;


    public StartPage(AutomationInfo automationInfo){
        WebFactory webFactory = new WebFactory(automationInfo);

        //Controls
        SSNTextBox = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_StartFV_txbSSN");
        confirmSSNTextBox = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_StartFV_txbSSNConfirmed");
        firstName = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_StartFV_txbNameFirst");
        lastName = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_StartFV_txbNameLast");
        employeeNumber = (TextBox) webFactory.create(TextBox.class, "#ctl00_Content_StartFV_txbEmpNo");
    }
}
