package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Link extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public Link(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public Link(IBy selector) {
        this(null, selector);
    }
}
