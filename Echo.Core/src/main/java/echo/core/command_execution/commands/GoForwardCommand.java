package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Move forward a single entry in the browser's history.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.GoForward();</p>
 * <p>
 * Does nothing if we are on the latest page viewed.
 */
public class GoForwardCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="GoForwardCommand"/> class.
     *
     * @param log The logger.
     */
    public GoForwardCommand(ILog log) {
        this(new ParameterObject(log, Resources.getString("GoForwardCommand_Info")), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="GoForwardCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public GoForwardCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.GoForward(getParameterObject());
    }
}
