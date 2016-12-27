package main.samplegrid;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.web.RowActions;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGridHeaders extends RowActions<MyGridHeaders, MyGridRowElements> {
    public MyGridHeaders(AutomationInfo automationInfo, IBy selector) {
        super(automationInfo, selector, MyGridHeaders.class, MyGridRowElements.class);
    }

    public MyGridHeaders material(String value) {
        return findRow(value);
    }

    public MyGridHeaders quantity(String value){
        return findRow(value);
    }

    public MyGridHeaders unitPrice(String value){
        return findRow(value);
    }

}
