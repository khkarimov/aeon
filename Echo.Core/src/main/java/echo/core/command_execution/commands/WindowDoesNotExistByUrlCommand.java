package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Checks for the absense of a window by URL.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.WindowDoesNotExistByTitle("windowURL");</p>
 */
public class WindowDoesNotExistByUrlCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="WindowDoesNotExistByUrlCommand" /> class.
     *
     * @param log                 The logger.
     * @param url                 The Url of the window.
     * @param currentWindowHandle The current handler.
     */
    public WindowDoesNotExistByUrlCommand(ILog log, String url, String currentWindowHandle)

    {
        this(new ParameterObject(log, String.format(Resources.getString("WindowDoesNotExistCommand_Info"), "<original>")), new WebCommandInitializer());
        getParameterObject().getWeb().setUrlString(url);
        getParameterObject().getWeb().setHandle(currentWindowHandle);
    }

    /**
     * Initializes a new instance of the <see cref="WindowDoesNotExistByUrlCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public WindowDoesNotExistByUrlCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.WindowDoesNotExistByUrl(getParameterObject());
    }
}
