package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Drag and Drop from one IBy to another IBy.
 */
public class DragAndDropCommand extends WebControlCommand {

    IByWeb targetElement;

    /**
     * Initializes a new instance of the {@link DragAndDropCommand} class.
     *
     * @param dropElement        The element to be dragged
     * @param targetElement      The target element.
     * @param commandInitializer The command initializer
     */
    public DragAndDropCommand(IByWeb dropElement, IByWeb targetElement, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("DragAndDropCommand_Info"), dropElement, targetElement), dropElement, commandInitializer);
        this.targetElement = targetElement;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver
     * @param control The web element
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.dragAndDrop(control, targetElement);
    }
}
