package echo.core.command_execution.commands.web;

/**
 * Created by Salvador Gandara on 6/28/2016.
 */

import echo.core.command_execution.commands.Command;
import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.drivers.IDriver;
import echo.core.framework_abstraction.drivers.IWebDriver;

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
     * Initializes a new instance of the <see cref="Command"/> class
     *
     * @param log         The log.
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public UploadFileDialogCommand(ILog log, IBy selector, ICommandInitializer initializer, String path) {
        super(log, Resources.getString("UploadFileDialogCommand_Info"), initializer);
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
