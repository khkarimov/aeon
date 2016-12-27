package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.interfaces.IByJQuery;
import echo.core.common.web.selectors.By;

/**
 * Created by AdamC on 4/13/2016.
 */
public class RowElements {
    protected AutomationInfo automationInfo;
    protected IBy selector;

    public RowElements(AutomationInfo info, IBy selector) {
        this.automationInfo = info;
        this.selector = selector;
    }
}
