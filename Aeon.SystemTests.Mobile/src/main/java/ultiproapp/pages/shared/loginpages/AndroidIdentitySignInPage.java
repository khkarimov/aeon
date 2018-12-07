package ultiproapp.pages.shared.loginpages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.mobile.selectors.ByMobile;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

public class AndroidIdentitySignInPage extends Page {

    public TextBox usernameField;
    public TextBox passwordField;
    public Button signInButton;
    public Label signInLabel;

    public AndroidIdentitySignInPage(AutomationInfo info){

        usernameField = new TextBox(info, ByMobile.id("idToken1"));
        passwordField = new TextBox(info, ByMobile.id("idToken2"));
        signInButton = new Button(info, ByMobile.id("idToken4_0"));
        signInLabel = new Label(info, ByMobile.id("txtSignIn"));

    }
}
