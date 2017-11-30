package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.Select;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * The login page.
 */
public class LoginPage extends Page {

    public TextBox userNameTextBox;
    public TextBox passwordTextBox;
    public Button loginButton;
    public Label warningMessage;
    public Select languageSelect;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public LoginPage(AutomationInfo automationInfo) {
        userNameTextBox = new TextBox(automationInfo, By.cssSelector("input[id*='UserName']"));
        passwordTextBox = new TextBox(automationInfo, By.cssSelector("input[id*='Password']"));
        loginButton = new Button(automationInfo, By.cssSelector("input[id*='LoginButton']"));
        languageSelect = new Select(automationInfo, By.cssSelector("select[name*='languagesSelection']"));
        warningMessage = new Label(automationInfo, By.cssSelector("div[id*='ctl00_Content_warnMsg']"));
    }
}
