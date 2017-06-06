package main;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;

public class SampleUltihome {
    private AutomationInfo info;

    public TextBox userField;
    public TextBox passField;
    public Button loginButton;

    public SampleUltihome(AutomationInfo info) {
        userField = new TextBox(info, By.cssSelector("#txtUserName"));
        passField = new TextBox(info, By.cssSelector("#txtPassword"));
        loginButton = new Button(info, By.cssSelector("button"));
    }
}
