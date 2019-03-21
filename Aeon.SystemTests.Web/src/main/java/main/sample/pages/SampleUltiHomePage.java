package main.sample.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.TextBox;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;

/**
 * Models the UltiHome login page.
 */
public class SampleUltiHomePage extends Page {

    public TextBox userField;
    public TextBox passField;
    public Button loginButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public SampleUltiHomePage(AutomationInfo automationInfo) {
        userField = new TextBox(automationInfo, By.cssSelector("#userName"));
        passField = new TextBox(automationInfo, By.cssSelector("#password"));
        loginButton = new Button(automationInfo, By.cssSelector("#login"));
    }
}
