package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.web.DragAndDropCommand;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by Administrator on 6/13/2016.
 */
public class ListItem extends Element {
    private AutomationInfo info;
    private IBy selector;

    public ListItem(AutomationInfo info, IBy selector){
        super(selector);
        this.info = info;
        this.selector = selector;
    }

    public void DragAndDrop(IBy dropTarget){
        info.getCommandExecutionFacade().Execute(info,
                new DragAndDropCommand(
                        info.getLog(),
                        selector,
                        dropTarget));
    }
}
