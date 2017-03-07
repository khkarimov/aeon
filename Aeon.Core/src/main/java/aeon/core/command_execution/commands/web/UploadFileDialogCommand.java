package aeon.core.command_execution.commands.web;

/**
 * Created by Salvador Gandara on 6/28/2016.
 */

import aeon.core.command_execution.commands.Command;
import aeon.core.command_execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.drivers.IDriver;
import aeon.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks a type input type=file.
 */

/**
 * Should consider extending from the WebControlCommand class since ultimately all its doing
 * is clicking on an HTML input tag
 */
public class UploadFileDialogCommand extends Command {
    private IBy selector;
    private String path;

    /**
     * Initializes a new instance of the {@link UploadFileDialogCommand} class
     *
     * @param log         The log.
     * @param selector    The selector.
     * @param initializer The command initializer.
     * @param path        The path of the file to select.
     */
    public UploadFileDialogCommand(ILog log, IBy selector, ICommandInitializer initializer, String path) {
        super(log, String.format(Locale.getDefault(), Resources.getString("UploadFileDialogCommand_Info"), path), initializer);
        this.selector = selector;
        this.path = path;
    }


    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).UploadFileDialog(getGuid(), selector, path);
    }
}
