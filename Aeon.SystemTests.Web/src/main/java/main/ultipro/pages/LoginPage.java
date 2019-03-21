package main.ultipro.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Dropdown;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

/**
 * The login page.
 */
public class LoginPage extends Page {

    public TextBox userNameTextBox;
    public TextBox passwordTextBox;
    public Button loginButton;
    public Label warningMessage;
    public Dropdown languageDropdown;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public LoginPage(AutomationInfo automationInfo) {
        userNameTextBox = new TextBox(automationInfo, By.cssSelector("input[id*='UserName']"));
        passwordTextBox = new TextBox(automationInfo, By.cssSelector("input[id*='Password']"));
        loginButton = new Button(automationInfo, By.cssSelector("input[id*='LoginButton']"));
        languageDropdown = new Dropdown(automationInfo, By.cssSelector("select[name*='languagesSelection']"));
        warningMessage = new Label(automationInfo, By.cssSelector("div[id*='ctl00_Content_warnMsg']"));
    }
}
