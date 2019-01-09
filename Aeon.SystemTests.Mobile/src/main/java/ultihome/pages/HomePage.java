package ultihome.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * Models the UltiHome login page.
 */
public class HomePage extends Page {

    public TextBox userField;
    public TextBox passField;
    public Button loginButton;

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     */
    public HomePage(AutomationInfo info) {
        userField = new TextBox(info, By.cssSelector("#txtUserName"));
        passField = new TextBox(info, By.cssSelector("#txtPassword"));
        loginButton = new Button(info, By.cssSelector("button"));
    }
}
