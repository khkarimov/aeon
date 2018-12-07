package ultiproapp.pages.shared.loginpages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

public class WebIdentitySignInPage extends Page {

    public TextBox usernameField;
    public TextBox passwordField;
    public Button signInButton;
    public Link forgotPasswordLink;

    public WebIdentitySignInPage(AutomationInfo info){

        usernameField = new TextBox(info, By.cssSelector("input[id=\"idToken1\"]"));
        passwordField = new TextBox(info, By.cssSelector("input[id=\"idToken2\"]"));
        signInButton = new Button(info, By.cssSelector("input[id=\"idToken4_0\"]"));
        forgotPasswordLink = new Link(info, By.cssSelector("a[id=\"forgot_password_link\"]"));

    }

}
