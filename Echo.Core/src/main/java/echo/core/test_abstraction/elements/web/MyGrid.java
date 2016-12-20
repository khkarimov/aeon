package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGrid extends rid<MyGridHeaders>G {
    public MyGrid(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public MyGrid(IBy selector) {
        super(selector);
    }
}
