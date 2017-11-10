package main.sample.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;
import aeon.core.testabstraction.models.Page;

public class SampleUltiHomePage extends Page {

    public TextBox userField;
    public TextBox passField;
    public Button loginButton;

    public SampleUltiHomePage(AutomationInfo info) {
        userField = new TextBox(info, By.cssSelector("#txtUserName"));
        passField = new TextBox(info, By.cssSelector("#txtPassword"));
        loginButton = new Button(info, By.cssSelector("button"));
    }
}
