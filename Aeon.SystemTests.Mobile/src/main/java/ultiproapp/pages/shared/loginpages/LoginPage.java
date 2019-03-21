package ultiproapp.pages.shared.loginpages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Link;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

/**
 * Login Page.
 */
public class LoginPage extends Page {

    public TextBox usernameInputField;
    public TextBox passwordInputField;
    public Label invalidCredentialsLabel;
    public Button signInButton;
    public Label companyAccessCodeLabel;
    public Button clearAccessCodeButton;
    public Link supportLink;

    /**
     * Constructs a Login Page.
     *
     * @param automationInfo Automation info
     */
    public LoginPage(AutomationInfo automationInfo) {
        usernameInputField = new TextBox(automationInfo, By.cssSelector("input[aria-labelledby=\"lbl-3\"]"));
        passwordInputField = new TextBox(automationInfo, By.cssSelector("input[aria-labelledby=\"lbl-4\"]"));
        signInButton = new Button(automationInfo, By.cssSelector("ion-button[data-automation=\"sign-in-button\"]"));
        companyAccessCodeLabel = new Label(automationInfo, By.cssSelector(".login-graphic-footer .company-access-code"));
        clearAccessCodeButton = new Button(automationInfo, By.cssSelector(".login-graphic-footer [data-automation=\"access-code-clear-icon\"]"));
        supportLink = new Link(automationInfo, By.cssSelector(".login-graphic-footer button[class*=\"btn-support\"]"));
    }
}
