package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Label extends WebElement {
    public Label(AutomationInfo info, IBy selector) {
        super(info, selector);
    }
}
