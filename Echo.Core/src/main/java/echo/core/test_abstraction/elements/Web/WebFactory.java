package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;

/**
 * Created by RafaelT on 6/8/2016.
 */
public class WebFactory {
    private AutomationInfo info;
    public WebFactory (AutomationInfo info) {
        this.info = info;
    }
    public Button Button(String selector) {
        return new Button(info, By.CssSelector(selector));
    }
    public TextBox TextBox(String selector) {
        return new TextBox(info, By.CssSelector(selector));
    }
    public Link Link(String selector) {
        return new Link(info, By.CssSelector(selector));
    }
    public Select Select(String selector) {
        return new Select(info, By.CssSelector(selector));
    }
    public Label Label(String selector) {
        return new Label(By.CssSelector(selector));
    }
}
