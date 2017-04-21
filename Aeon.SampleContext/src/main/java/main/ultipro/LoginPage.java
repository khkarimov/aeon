package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.Select;
import aeon.core.testabstraction.elements.web.TextBox;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox userNameTextBox;
    public TextBox passwordTextBox;
    public Button loginButton;
    public Label warningMessage;
    public Select languageSelect;

    public LoginPage(AutomationInfo automationInfo) {
        userNameTextBox = new TextBox(automationInfo, By.cssSelector( "input[id*='UserName']"));
        passwordTextBox = new TextBox(automationInfo, By.cssSelector("input[id*='Password']"));
        loginButton = new Button(automationInfo, By.cssSelector("input[id*='loginButton']"));
        languageSelect = new Select(automationInfo, By.cssSelector("select[name*='languagesSelection']"));
        warningMessage = new Label(automationInfo, By.cssSelector("div[id*='ctl00_Content_warnMsg']"));
    }
}
