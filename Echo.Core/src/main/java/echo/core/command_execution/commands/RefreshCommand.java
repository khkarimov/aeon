package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

/**
 * Refreshes the current page
 */
public class RefreshCommand extends Command {

    /**
     * Initializes a new instance of RefreshCommand
     * @param log the logger
     */
    public RefreshCommand(ILog log) {
        super(new ParameterObject(log, Resources.getString("RefreshCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of RefreshCommand
     * @param parameterObject the params object
     * @param commandInitializer the command initializer
     */
    public RefreshCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command
     * @param frameworkAbstractionFacade The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if(frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException();
        }

        frameworkAbstractionFacade.Refresh(getParameterObject());
    }
}
