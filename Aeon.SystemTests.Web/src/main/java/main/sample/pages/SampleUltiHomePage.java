package main.sample.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;

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
     * @param info The automation info object to use.
     */
    public SampleUltiHomePage(AutomationInfo info) {
        userField = new TextBox(info, By.cssSelector("#username"));
        passField = new TextBox(info, By.cssSelector("#password"));
        loginButton = new Button(info, By.cssSelector("body > div > div.ping-body-container > div > form > div.ping-buttons > a"));
    }
}
