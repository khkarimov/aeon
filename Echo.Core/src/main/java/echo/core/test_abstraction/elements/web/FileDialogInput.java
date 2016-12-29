package echo.core.test_abstraction.elements.web;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.web.*;
import echo.core.common.web.interfaces.IBy;

import java.util.ArrayList;

/**
 * Created by SebastianR on 6/3/2016.
 */
public class FileDialogInput extends WebElement {
    private AutomationInfo info;
    private IBy selector;
    private Iterable<IBy> switchMechanism;

    public FileDialogInput(AutomationInfo info, IBy selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    public FileDialogInput(AutomationInfo info, IBy selector, Iterable<IBy> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }


    public void OpenFileDialog() {
        info.getCommandExecutionFacade().Execute(info,
                new OpenFileDialogCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Takes in the path of a file
     * And then types in the path of the file.
     * REQUIRES OpenFileDialog to be called first.
     *
     * @param path
     */
    public void SelectFileDialog(String path) {
        info.getCommandExecutionFacade().Execute(info,
                new SelectFileDialogCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }

    /**
     * Takes in the path of a file,
     * Clicks upload file button to open dialog box,
     * And then types in the path of the file using the keyboard.
     * DOES NOT REQUIRE OpenFileDialog to be called first.
     *
     * @param path
     */
    public void UploadFileDialog(String path) {
        info.getCommandExecutionFacade().Execute(info,
                new UploadFileDialogCommand(
                        info.getLog(),
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }


}
