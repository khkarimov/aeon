package echo.core.test_abstraction.elements;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.OpenFileDialogCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

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
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>())));
    }
}
