package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

/**
 * <p>Move back a single entry in the browser's history.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.GoBack();</p>
 */
public class GoBackCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="GoBackCommand"/> class.
     *
     * @param log The logger.
     */
    public GoBackCommand(ILog log) {
        this(new ParameterObject(log, Resources.getString("GoBackCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="GoBackCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public GoBackCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        frameworkAbstractionFacade.GoBack(getParameterObject());
    }
}
