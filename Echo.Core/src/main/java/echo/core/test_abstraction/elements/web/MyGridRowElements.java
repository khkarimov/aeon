package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.common.web.selectors.ByJQuery;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridRowElements extends RowElements{
//    private AutomationInfo automationInfo;
//    private IBy selector;
    public Button checkBoxButton;

    public MyGridRowElements(AutomationInfo info, IBy selector) {
        super(info, selector);
        checkBoxButton = new Button(info, selector.ToJQuery().find("span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center"));
    }
}
