package echo.core.test_abstraction.product;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.models.Browser;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class WebProduct extends Product {
    public Browser browser;

    public WebProduct() {

    }

    protected WebProduct(AutomationInfo automationInfo) {
        super(automationInfo);
        browser = new Browser(automationInfo);
    }

    @Override
    protected void afterLaunch() {
        browser = new Browser(getAutomationInfo());
    }
}
