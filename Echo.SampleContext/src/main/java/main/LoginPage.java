package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Button;
import echo.core.test_abstraction.elements.TextBox;
import echo.core.test_abstraction.elements.Select;

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
        WebFactory web = new WebFactory(automationInfo);
        UserNameTextBox = web.TextBox("input[id*='UserName']");
        PasswordTextBox = web.TextBox("input[id*='Password']");
        LoginButton = web.Button("input[id*='LoginButton']");
        LanguageSelect = web.Select("select[name*='languagesSelection']");
    }
}
