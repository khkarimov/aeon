package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.OpenFileDialogCommand;
import echo.core.command_execution.commands.web.SelectFileDialogCommand;
import echo.core.command_execution.commands.web.WebControlFinder;
import echo.core.command_execution.commands.web.WebSelectorFinder;
import echo.core.common.web.interfaces.IBy;
import echo.core.test_abstraction.elements.Element;

import java.util.ArrayList;

/**
 * Created by SebastianR on 6/3/2016.
 */
public class FileDialogInput extends Element {
    private AutomationInfo info;
    private IBy selector;

    public FileDialogInput(AutomationInfo info, IBy selector){
        super(selector, info);
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

    /**
     * Takes in the path of a file
     * Clicks the file dialog button indicated by the context, and then types in the path of the file.
     * Does not require OpenFileDialog to be called first.
     * @param path
     */
    public void SelectFileDialog(String path){
        info.getCommandExecutionFacade().Execute(info,
                new SelectFileDialogCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), new ArrayList<>()),
                        path));
    }


}
