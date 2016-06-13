package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.TextBox;
import echo.core.test_abstraction.elements.web.Select;
import echo.core.test_abstraction.elements.web.WebFactory;

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
        UserNameTextBox = WebFactory.createTextBox("input[id*='UserName']", automationInfo);
        PasswordTextBox = WebFactory.createTextBox("input[id*='Password']", automationInfo);
        LoginButton = WebFactory.createButton("input[id*='LoginButton']", automationInfo);
        LanguageSelect = WebFactory.createSelect("select[name*='languagesSelection']", automationInfo);
    }
}
