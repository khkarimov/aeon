package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridRowElements extends RowElements {
    public MyGridRowElements(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public MyGridRowElements(IBy selector) {
        super(selector);
    }

    public final WebElement removeButton = new WebElement(this.automationInfo, null);
    public final WebElement personName = null;
}
