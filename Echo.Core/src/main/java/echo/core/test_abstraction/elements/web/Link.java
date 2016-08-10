package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.ClickCommand;
import echo.core.command_execution.commands.web.RightClickCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Link extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public Link(AutomationInfo info, IBy selector)
    {
        super(info, selector);
        this.info= info;
        this.selector = selector;
    }
}
