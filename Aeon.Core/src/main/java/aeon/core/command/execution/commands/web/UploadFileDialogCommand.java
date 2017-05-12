package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Clicks a type input type=file.
 * Should consider extending from the WebControlCommand class since ultimately all its doing
 * is clicking on an HTML input tag.
 */
public class UploadFileDialogCommand extends Command {

    private IBy selector;
    private String path;

    /**
     * Initializes a new instance of the {@link UploadFileDialogCommand} class.
     *
     * @param selector    The selector.
     * @param initializer The command initializer.
     * @param path        The path of the file to select.
     */
    public UploadFileDialogCommand(IBy selector, ICommandInitializer initializer, String path) {
        super(String.format(Locale.getDefault(), Resources.getString("UploadFileDialogCommand_Info"), path), initializer);
        this.selector = selector;
        this.path = path;
    }


    @Override
    protected void driverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).uploadFileDialog(selector, path);
    }
}
