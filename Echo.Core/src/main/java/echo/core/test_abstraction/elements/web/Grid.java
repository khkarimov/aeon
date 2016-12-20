package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Grid<T extends RowActions> extends WebElement {
    public final T RowBy = (T) new Object();

    public Grid(AutomationInfo info, IBy selector) {
        super(info, selector);
    }

    public Grid(IBy selector) {
        super(selector);
    }
}
