package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Grid<T extends RowActions> extends WebElement {
    public T RowBy;
    
    public Grid(AutomationInfo info, By selector, T rowBy) {
        super(info, selector);
        this.RowBy = rowBy;
    }
}
