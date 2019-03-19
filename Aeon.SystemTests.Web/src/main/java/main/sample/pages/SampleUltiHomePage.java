package main.sample.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

/**
 * Models the UltiHome login page.
 */
public class SampleUltiHomePage implements Page {

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
