package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public abstract class Grid<T extends RowActions> extends WebElement {
    public T RowBy;
    
    public Grid(AutomationInfo info, IBy selector, T rowBy) {
        super(info, selector);
        this.RowBy = rowBy;
    }
}
