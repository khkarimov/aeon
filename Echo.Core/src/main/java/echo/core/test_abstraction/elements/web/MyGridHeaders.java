package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridHeaders extends RowActions<MyGridHeaders, MyGridRowElements> {
    public MyGridHeaders(AutomationInfo automationInfo, By selector) {
        super(automationInfo, selector, MyGridHeaders.class, MyGridRowElements.class);
    }

//    public MyGridHeaders name(String value) {
//        return findRow(value, new By.ByCssSelector("[id$=header_name"));
//    }
//
//    public MyGridHeaders address(String value) {
//        return findRow(value, new By.ByCssSelector("[id$=header_address"));
//    }
}
