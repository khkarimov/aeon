package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.interfaces.IByWeb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Handles file dialog interactions.
 */
public class FileDialogInput extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private Iterable<IByWeb> switchMechanism;

    static Logger log = LogManager.getLogger(FileDialogInput.class);

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info     The automation info.
     * @param selector IBy selector that will identify the element.
     */
    public FileDialogInput(AutomationInfo info, IByWeb selector) {
        super(info, selector);
        this.info = info;
        this.selector = selector;
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param info            The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public FileDialogInput(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        this.info = info;
        this.selector = selector;
        this.switchMechanism = switchMechanism;
    }


    /**
     * Opens a file dialog window.
     */
    public void openFileDialog() {
        log.warn("DEPRECATED: The use of the \"openFileDialog\" command " +
                "has been deprecated and will be removed in future " +
                "versions of Aeon. Please use \"uploadFileDialog\" " +
                "instead");
        info.getCommandExecutionFacade().execute(info,
                new OpenFileDialogCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism)));
    }

    /**
     * Takes in the path of a file
     * And then types in the path of the file.
     * REQUIRES openFileDialog to be called first.
     *
     * @param path The path to the file to be selected.
     */
    public void selectFileDialog(String path) {
        log.warn("DEPRECATED: The use of the \"selectFileDialog\" command " +
                "has been deprecated and will be removed in future " +
                "versions of Aeon. Please use \"uploadFileDialog\" " +
                "instead");
        info.getCommandExecutionFacade().execute(info,
                new SelectFileDialogCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }

    /**
     * Takes in the path of a file,
     * Clicks upload file button to open dialog box,
     * And then types in the path of the file using the keyboard.
     * DOES NOT REQUIRE openFileDialog to be called first.
     *
     * @param path The path to the file to be selected.
     */
    public void uploadFileDialog(String path) {
        info.getCommandExecutionFacade().execute(info,
                new UploadFileDialogCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }
}
