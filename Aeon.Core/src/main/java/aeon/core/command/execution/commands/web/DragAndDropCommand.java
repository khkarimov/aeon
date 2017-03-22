package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created By SebastianR on 6/1/2016.
 */

/**
 * Drag and Drop from one IBy to another IBy
 */
public class DragAndDropCommand extends WebControlCommand {
    IBy targetElement;

    /**
     * Initializes a new instance of the DragAndDropCommand
     *                The logger
     * @param dropElement        The element to be dragged
     * @param targetElement      The target element.
     * @param commandInitializer The command initalizer
     */
    public DragAndDropCommand(IBy dropElement, IBy targetElement, ICommandInitializer commandInitializer) {
        super(String.format(Locale.getDefault(), Resources.getString("DragAndDropCommand_Info"), dropElement, targetElement), dropElement, commandInitializer);
        this.targetElement = targetElement;
    }

    /**
     * Provides the logic for the command
     *
     * @param driver  The web driver
     * @param control The web element
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        if (control == null) {
            throw new IllegalArgumentException("control");
        }
        driver.dragAndDrop(control, targetElement);
    }
}
