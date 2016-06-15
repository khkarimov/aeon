package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.ClickCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Link extends Element {

    private AutomationInfo info;
    private IBy selector;
    public Link(AutomationInfo info, IBy selector)
    {
        super(selector, info);
        this.info= info;
        this.selector = selector;
    }

    public Link(IBy selector) {
        super(selector);
    }

    public void Click()
    {
        info.getCommandExecutionFacade().Execute(info,
                new ClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
