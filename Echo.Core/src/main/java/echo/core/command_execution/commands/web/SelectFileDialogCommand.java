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

import java.util.Locale;

/**
 * Clicks a type input type=file.
 */

/**
 * Should consider extending from the WebControlCommand class since ultimately all its doing
 * is clicking on an HTML input tag
 */
public class SelectFileDialogCommand extends Command {
    private IBy selector;
    private String path;

    /**
     * Initializes a new instance of the {@link SelectFileDialogCommand} class
     *
     * @param log         The log.
     * @param selector    The selector.
     * @param initializer The command initializer.
     */
    public SelectFileDialogCommand(ILog log, IBy selector, ICommandInitializer initializer, String path) {
        super(log, String.format(Locale.getDefault(), Resources.getString("SelectFileDialogCommand_Info"), path), initializer);
        this.selector = selector;
        this.path = path;
    }


    @Override
    protected void DriverDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }
        ((IWebDriver) driver).SelectFileDialog(getGuid(), selector, path);
    }
}
