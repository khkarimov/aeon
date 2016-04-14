package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;

/**
 * <p>Maximizes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.Maximize();</p>
 */
public class MaximizeCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="MaximizeCommand"/> class.
     *
     * @param log The logger.
     */
    public MaximizeCommand(ILog log) {
        super(new ParameterObject(log, Resources.getString("MaximizeCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="MaximizeCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public MaximizeCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        driver.Maximize(getParameterObject());
    }
}
