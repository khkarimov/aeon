package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.Label;
import echo.core.test_abstraction.elements.web.Select;
import echo.core.test_abstraction.elements.web.TextBox;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox userNameTextBox;
    public TextBox passwordTextBox;
    public Button loginButton;
    public Label WarningMessage;
    public Select languageSelect;
    private AutomationInfo automationInfo;

    public LoginPage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(this.automationInfo);
        userNameTextBox = (TextBox) web.create(TextBox.class, "input[id*='UserName']");
        passwordTextBox = (TextBox) web.create(TextBox.class, "input[id*='Password']");
        loginButton = (Button) web.create(Button.class, "input[id*='LoginButton']");
        languageSelect = (Select) web.create(Select.class, "select[name*='languagesSelection']");
        //WarningMessage = (Label) web.create(Label.class, "div[id*='ctl00_Content_warnMsg']");
    }
}
