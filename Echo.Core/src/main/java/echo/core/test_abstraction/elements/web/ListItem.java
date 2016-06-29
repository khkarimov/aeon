package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.DragAndDropCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/17/2016.
 */
public class ListItem extends WebElement {
    private AutomationInfo info;
    private IBy selector;

    public ListItem(AutomationInfo info, IBy selector){
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public void DragAndDrop(IBy targetElement) {
        info.getCommandExecutionFacade().Execute(info,
                new DragAndDropCommand(info.getLog(),
                        selector,
                        targetElement,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
