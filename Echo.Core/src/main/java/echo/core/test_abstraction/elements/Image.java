package echo.core.test_abstraction.elements;

/**
 * Created by SebastianR on 6/3/2016.
 */
import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.web.DoubleClickCommand;
import echo.core.common.web.interfaces.IBy;

public class Image extends Element {
    private AutomationInfo info;
    private IBy selector;

    public Image(AutomationInfo info, IBy selector){
        super(selector);
        this.info = info;
        this.selector = selector;
    }

    public void DoubleClick(){
        info.getCommandExecutionFacade().Execute(info,
                new DoubleClickCommand(
                        info.getLog(),
                        selector,
                        getWebCommandInitializer()));
    }
}
