package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.factories.WebFactory;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.Label;
import echo.core.test_abstraction.elements.web.Select;
import echo.core.test_abstraction.elements.web.TextBox;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class LoginPage {
    public TextBox UserNameTextBox;
    public TextBox PasswordTextBox;
    public Button LoginButton;
    public Label WarningMessage;
    public Select LanguageSelect;
    private AutomationInfo automationInfo;

    public LoginPage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
//        WebFactory web = new WebFactory(this.automationInfo);
//        UserNameTextBox = (TextBox) web.create(TextBox.class, "input[id*='UserName']");
//        PasswordTextBox = (TextBox) web.create(TextBox.class, "input[id*='Password']");
//        LoginButton = (Button) web.create(Button.class, "input[id*='LoginButton']");
//        LanguageSelect = (Select) web.create(Select.class, "select[name*='languagesSelection']");
//        WarningMessage = (Label) web.create(Label.class, "div[id*='ctl00_Content_warnMsg']");
        UserNameTextBox = new TextBox(automationInfo, By.CssSelector( "input[id*='UserName']"));
        PasswordTextBox = new TextBox(automationInfo, By.CssSelector("input[id*='Password']"));
        LoginButton = new Button(automationInfo, By.CssSelector("input[id*='LoginButton']"));
        LanguageSelect = new Select(automationInfo, By.CssSelector("select[name*='languagesSelection']"));
        WarningMessage = new Label(automationInfo, By.CssSelector("div[id*='ctl00_Content_warnMsg']"));
    }
}
