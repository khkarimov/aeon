package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Clicks a type input type=file.
 */
public class OpenFileDialogCommand extends WebControlCommand {

    /**
     * Initializes a new instance of the {@link Command} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public OpenFileDialogCommand(IByWeb selector, ICommandInitializer initializer) {
        super(Resources.getString("OpenFileDialogCommand_Info"), selector, initializer);
    }

    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.openFileDialog(control);
    }
}
