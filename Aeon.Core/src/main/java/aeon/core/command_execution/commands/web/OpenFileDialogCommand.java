package aeon.core.command_execution.commands.web;

/**
 * Created by SebastianR on 5/31/2016.
 */

import aeon.core.command_execution.commands.Command;
import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

/**
 * Clicks a type input type=file.
 */

/**
 * Should consider extending from the WebControlCommand class since ultimately all its doing
 * is clicking on an HTML input tag
 */
public class OpenFileDialogCommand extends Command {
    private IBy selector;

    /**
     * Initializes a new instance of the {@link Command} class
     *
     * @param log         The log.
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public OpenFileDialogCommand(ILog log, IBy selector, ICommandInitializer initializer) {
        super(log, Resources.getString("OpenFileDialogCommand_Info"), initializer);
        this.selector = selector;
    }


    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).OpenFileDialog(getGuid(), selector);
    }
}
