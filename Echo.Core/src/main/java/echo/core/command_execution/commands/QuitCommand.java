package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

public class QuitCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="QuitCommand"/> class.
     *
     * @param log The logger.
     */
    public QuitCommand(ILog log) {
        this(new ParameterObject(log, Resources.getString("QuitCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="QuitCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public QuitCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        frameworkAbstractionFacade.Quit(getParameterObject());
    }
}
