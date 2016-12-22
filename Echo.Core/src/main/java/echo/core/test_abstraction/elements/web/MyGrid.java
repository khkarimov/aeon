package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGrid extends Grid<MyGridHeaders> {
    public MyGrid(AutomationInfo info, By selector, MyGridHeaders gridHeaders) {
        super(info, selector, gridHeaders);
    }
}
