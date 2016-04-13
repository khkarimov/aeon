package echo.core.test_abstraction.context;

import echo.core.command_execution.AutomationInfo;
import echo.core.framework_abstraction.IDriverFactory;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class WebProduct<T extends IDriverFactory> extends Product<T> {
    public IBrowserObject Browser;

    public WebProduct() {
        Browser =
    }

    protected WebProduct(AutomationInfo automationInfo) {
        super(automationInfo);
    }
}
