package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridRowElements extends RowElements{
    private AutomationInfo automationInfo;
    private By selector;
    public Button checkBoxButton;

    public MyGridRowElements(AutomationInfo info, By selector) {
        super(info, selector);
        this.automationInfo = info;
        this.selector = selector;
        checkBoxButton = new Button(this.automationInfo, By.CssSelector(String.format("%1$s span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center", selector)));
    }
}
