package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

/**
 * <p>Switches focus to a specified window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.SwitchToWindowByUrl("url");</p>
 * <p>      Context.Browser.SwitchToWindowByUrl("url", setMainWindowBoolean);</p>
 */
public class SwitchToWindowByUrlCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="SwitchToWindowByUrlCommand"/> class.
     *
     * @param log   The logger.
     * @param value The URL to which to switch focus.
     */
    public SwitchToWindowByUrlCommand(ILog log, String value) {
        this(new ParameterObject(log, String.format(Resources.getString("SwitchToWindowCommand_Info"), value)), new WebCommandInitializer());
        getParameterObject().getWeb().setUrlString(value);
    }

    /**
     * Initializes a new instance of the <see cref="SwitchToWindowByUrlCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command Initializer.
     */
    public SwitchToWindowByUrlCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     * @return The current handler after the change.
     */
    @Override
    protected Object CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        return frameworkAbstractionFacade.SwitchToWindowByUrl(getParameterObject());
    }
}
