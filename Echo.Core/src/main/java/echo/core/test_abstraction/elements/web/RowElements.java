package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.interfaces.IByJQuery;

/**
 * Created by AdamC on 4/13/2016.
 */
public class RowElements extends WebElement {
    public RowElements(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public RowElements(IBy selector) {
        super(selector);
    }
}
