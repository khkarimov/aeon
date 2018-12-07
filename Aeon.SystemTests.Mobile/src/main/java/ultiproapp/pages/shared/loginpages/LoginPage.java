package ultiproapp.pages.shared.loginpages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

public class LoginPage extends Page {

    public TextBox usernameInputField;
    public TextBox passwordInputField;
    public Label invalidCredentialsLabel;
    public Button signInButton;
    public Label companyAccessCodeLabel;
    public Button clearAccessCodeButton;
    public Link supportLink;

    public LoginPage(AutomationInfo info) {

        usernameInputField = new TextBox(info, By.cssSelector("input[aria-labelledby=\"lbl-3\"]"));
        passwordInputField = new TextBox(info, By.cssSelector("input[aria-labelledby=\"lbl-4\"]"));
        signInButton = new Button(info, By.cssSelector("ion-button[data-automation=\"sign-in-button\"]"));
        companyAccessCodeLabel = new Label(info, By.cssSelector(".login-graphic-footer .company-access-code"));
        clearAccessCodeButton = new Button(info, By.cssSelector(".login-graphic-footer [data-automation=\"access-code-clear-icon\"]"));
        supportLink = new Link(info, By.cssSelector(".login-graphic-footer button[class*=\"btn-support\"]"));

    }
}
