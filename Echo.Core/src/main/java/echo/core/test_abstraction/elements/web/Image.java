package echo.core.test_abstraction.elements.web;

/**
 * Created by SebastianR on 6/3/2016.
 */
import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.DoubleClickCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.DragAndDropCommand;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

public class Image extends Element {
    private AutomationInfo info;
    private IBy selector;

    public Image(AutomationInfo info, IBy selector){
        super(selector, info);
        this.info = info;
        this.selector = selector;
    }

    public void DoubleClick(){
        info.getCommandExecutionFacade().Execute(info,
                new DoubleClickCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }

    public void DragAndDrop(IBy targetElement) {
        info.getCommandExecutionFacade().Execute(info,
                new DragAndDropCommand(info.getLog(),
                        selector,
                        targetElement,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
