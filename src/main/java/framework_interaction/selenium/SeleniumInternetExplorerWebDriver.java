package framework_interaction.selenium;

import common.JQueryStringType;
import common.logging.ILog;
import framework_abstraction.WebElement;
import framework_abstraction.webdriver.IJavaScriptFlowExecutor;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.util.UUID;

public class SeleniumInternetExplorerWebDriver extends SeleniumWebDriver {
    public SeleniumInternetExplorerWebDriver(WebDriver seleniumWebDriver, IJavaScriptFlowExecutor javaScriptExecutor, ILog log, boolean moveMouseToOrigin) {
        super(seleniumWebDriver, javaScriptExecutor, log, moveMouseToOrigin);
    }

    @Override
    public void FocusWindow(UUID guid) {
        ExecuteScript(guid, "if(!document.hasFocus()) { window.focus(); }");
    }

    // IE Driver black screenshot issue
    @Override
    public Image GetScreenshot(UUID guid) {
        return super.GetScreenshot(guid);
    }

    @Override
    protected void WrappedClick(UUID guid, WebElement element, java.util.List<Object> list) {
        ExecuteScript(guid, element.getSelector().ToJQuery().toString(JQueryStringType.ClickInvisibleElement));
    }
}
