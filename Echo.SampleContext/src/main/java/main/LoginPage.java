package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.TextBox;
import echo.core.test_abstraction.elements.web.Select;
import echo.core.test_abstraction.elements.factories.WebFactory;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox UserNameTextBox;
    public TextBox PasswordTextBox;
    public Button LoginButton;
    public Select LanguageSelect;
    private AutomationInfo automationInfo;

    public LoginPage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        WebFactory web = new WebFactory(this.automationInfo);
        UserNameTextBox = (TextBox) web.create(TextBox.class, "input[id*='UserName']");
        PasswordTextBox = (TextBox) web.create(TextBox.class, "input[id*='Password']");
        LoginButton = (Button) web.create(Button.class, "input[id*='LoginButton']");
        LanguageSelect = (Select) web.create(Select.class, "select[name*='languagesSelection']");
    }
}
