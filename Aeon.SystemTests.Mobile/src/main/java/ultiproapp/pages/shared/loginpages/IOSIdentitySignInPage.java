package ultiproapp.pages.shared.loginpages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.mobile.selectors.ByMobile;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

public class IOSIdentitySignInPage extends Page {

    public Button doneButton;
    public TextBox usernameField;
    public TextBox passwordField;
    public Button signInButton;

    public IOSIdentitySignInPage(AutomationInfo info) {

        doneButton = new Button(info, ByMobile.accessibilityId("Done"));
        usernameField = new TextBox(info, ByMobile.accessibilityId("Username"));
        passwordField = new TextBox(info, ByMobile.accessibilityId("Password"));
        signInButton = new Button(info, ByMobile.xpath("//XCUIElementTypeApplication/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeWebView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton"));

    }

}
