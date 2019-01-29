package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.SelectFileCommand;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IByWeb;


/**
 * Handles file dialog interactions.
 */
public class FileDialogInput extends WebElement {

    private AutomationInfo info;
    private IByWeb selector;
    private Iterable<IByWeb> switchMechanism;

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
     * Selects a file.
     *
     * @param path The path to the file to be selected.
     */
    public void selectFile(String path) {
        info.getCommandExecutionFacade().execute(info,
                new SelectFileCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }
}
