package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.TextBox;

/**
 * Created by SebastianR on 11/23/2016.
 */
public class NewHirePage {
    private AutomationInfo automationInfo;

    public TextBox SSNTextBox;
    public TextBox confirmSSNTextBox;
    public Button cancelButton;

    public NewHirePage(AutomationInfo automationInfo){
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(automationInfo);
        SSNTextBox = (TextBox) web.create(TextBox.class, "#ctl00_Content_StartFV_txbSSN");
        confirmSSNTextBox = (TextBox) web.create(TextBox.class, "#ctl00_Content_StartFV_txbSSNConfirmed");
        cancelButton = (Button) web.create(Button.class, "#ctl00_btnCancel");
    }
}
