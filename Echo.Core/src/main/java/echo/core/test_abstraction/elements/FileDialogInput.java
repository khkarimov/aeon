package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.web.OpenFileDialogCommand;
import echo.core.common.web.interfaces.IBy;

/**
 * Created by SebastianR on 6/3/2016.
 */
public class FileDialogInput extends Element{
    private AutomationInfo info;
    private IBy selector;

    public FileDialogInput(AutomationInfo info, IBy selector){
        super(selector);
        this.info = info;
        this.selector = selector;
    }

    public void OpenFileDialog(){
        info.getCommandExecutionFacade().Execute(info,
                new OpenFileDialogCommand(
                        info.getLog(),
                        selector,
                        getWebCommandInitializer()));
    }
}
