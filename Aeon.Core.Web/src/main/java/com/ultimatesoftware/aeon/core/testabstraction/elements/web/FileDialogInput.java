package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.SelectFileCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebControlFinder;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.WebSelectorFinder;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;


/**
 * Handles file dialog interactions.
 */
public class FileDialogInput extends WebElement {

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo The automation info.
     * @param selector       IBy selector that will identify the element.
     */
    public FileDialogInput(AutomationInfo automationInfo, IByWeb selector) {
        super(automationInfo, selector);
    }

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param automationInfo  The automation info.
     * @param selector        IBy selector that will identify the element.
     * @param switchMechanism The switch mechanism.
     */
    public FileDialogInput(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
    }

    /**
     * Selects a file.
     *
     * @param path The path to the file to be selected.
     */
    public void selectFile(String path) {
        automationInfo.getCommandExecutionFacade().execute(automationInfo,
                new SelectFileCommand(
                        selector,
                        new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism),
                        path));
    }
}
