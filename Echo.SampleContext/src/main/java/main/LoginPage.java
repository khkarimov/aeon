package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Button;
import echo.core.test_abstraction.elements.TextBox;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox UserNameTextBox;
    public TextBox PasswordTextBox;
    public Button LoginButton;
    private AutomationInfo automationInfo;

    public LoginPage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        UserNameTextBox = new TextBox(automationInfo, By.CssSelector("input[id*='UserName']"));
        PasswordTextBox = new TextBox(automationInfo, By.CssSelector("input[id*='Password']"));
        LoginButton = new Button(automationInfo, By.CssSelector("input[id*='LoginButton']"));
    }
}
