package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class Button extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public Button(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public Button(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism){
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
    }
}
