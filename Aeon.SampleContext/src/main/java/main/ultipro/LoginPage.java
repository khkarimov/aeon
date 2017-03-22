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
