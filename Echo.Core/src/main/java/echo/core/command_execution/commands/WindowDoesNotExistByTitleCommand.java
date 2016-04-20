package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Checks for the absense of a window by title.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.WindowDoesNotExistByTitle("windowTitle");</p>
 */
public class WindowDoesNotExistByTitleCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="WindowDoesNotExistByTitleCommand" /> class.
     *
     * @param log                 The logger.
     * @param windowTitle         The title of the window.
     * @param currentWindowHandle The current handler.
     */
    public WindowDoesNotExistByTitleCommand(ILog log, String windowTitle, String currentWindowHandle) {
        this(new ParameterObject(log, String.format(Resources.getString("WindowDoesNotExistCommand_Info"), "<original>")), new WebCommandInitializer());
        getParameterObject().getWeb().setWindowTitle(windowTitle);
        getParameterObject().getWeb().setHandle(currentWindowHandle);
    }

    /**
     * Initializes a new instance of the <see cref="WindowDoesNotExistByTitleCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command Initializer.
     */
    public WindowDoesNotExistByTitleCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.WindowDoesNotExistByTitle(getParameterObject());
    }
}
