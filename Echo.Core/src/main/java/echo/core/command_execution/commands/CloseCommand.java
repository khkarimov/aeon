package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

/**
 * <p>Closes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.Close();</p>
 */
public class CloseCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="CloseCommand"/> class.
     *
     * @param log The logger.
     */
    public CloseCommand(ILog log) {
        this(new ParameterObject(log, Resources.getString("CloseCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="CloseCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public CloseCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        frameworkAbstractionFacade.Close(getParameterObject());
    }
}
