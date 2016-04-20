package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Switches focus to a specified window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.SwitchToWindow("windowTitle");</p>
 * <p>      Context.Browser.SwitchToWindow("windowTitle", setMainWindowBoolean);</p>
 */
public class SwitchToWindowByTitleCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="SwitchToWindowByTitleCommand"/> class.
     *
     * @param log   The logger.
     * @param title The title of the desired window.
     */
    public SwitchToWindowByTitleCommand(ILog log, String title) {
        this(new ParameterObject(log, String.format(Resources.getString("SwitchToWindowCommand_Info"), title)), new WebCommandInitializer());
        getParameterObject().getWeb().setWindowTitle(title);
    }

    /**
     * Initializes a new instance of the <see cref="SwitchToWindowByTitleCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public SwitchToWindowByTitleCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The current handler after the change.
     */
    @Override
    protected Object CommandDelegate(IWebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return driver.SwitchToWindowByTitle(getParameterObject());
    }
}
