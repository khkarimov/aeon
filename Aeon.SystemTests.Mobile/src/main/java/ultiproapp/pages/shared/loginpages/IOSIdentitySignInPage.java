package ultiproapp.pages.shared.loginpages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.mobile.selectors.ByMobile;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

/**
 * iOS Identity Sign-in Page.
 */
public class IOSIdentitySignInPage extends Page {

    public Button doneButton;
    public TextBox usernameField;
    public TextBox passwordField;
    public Button signInButton;

    /**
     * Constructs an iOS Identity Sign-in Page.
     *
     * @param automationInfo Automation info
     */
    public IOSIdentitySignInPage(AutomationInfo automationInfo) {

        doneButton = new Button(automationInfo, ByMobile.accessibilityId("Done"));
        usernameField = new TextBox(automationInfo, ByMobile.accessibilityId("Username"));
        passwordField = new TextBox(automationInfo, ByMobile.accessibilityId("Password"));
        signInButton = new Button(automationInfo, ByMobile.xpath("//XCUIElementTypeApplication/" +
                "XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/" +
                "XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/" +
                "XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/" +
                "XCUIElementTypeWebView/XCUIElementTypeOther/XCUIElementTypeOther/" +
                "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
                "XCUIElementTypeOther/XCUIElementTypeButton"));
    }
}
