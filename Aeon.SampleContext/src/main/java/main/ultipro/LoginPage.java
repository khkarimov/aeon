package main.ultipro;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.Button;
import aeon.core.test_abstraction.elements.web.Label;
import aeon.core.test_abstraction.elements.web.Select;
import aeon.core.test_abstraction.elements.web.TextBox;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox UserNameTextBox;
    public TextBox PasswordTextBox;
    public Button LoginButton;
    public Label WarningMessage;
    public Select LanguageSelect;

    public LoginPage(AutomationInfo automationInfo) {
        UserNameTextBox = new TextBox(automationInfo, By.CssSelector( "input[id*='UserName']"));
        PasswordTextBox = new TextBox(automationInfo, By.CssSelector("input[id*='Password']"));
        LoginButton = new Button(automationInfo, By.CssSelector("input[id*='LoginButton']"));
        LanguageSelect = new Select(automationInfo, By.CssSelector("select[name*='languagesSelection']"));
        WarningMessage = new Label(automationInfo, By.CssSelector("div[id*='ctl00_Content_warnMsg']"));
    }
}
