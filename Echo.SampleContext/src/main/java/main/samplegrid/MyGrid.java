package main.samplegrid;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.Grid;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGrid extends Grid<MyGridHeaders> {
    public MyGrid(MyGridHeaders gridHeaders) {
        super(gridHeaders);
    }
}
