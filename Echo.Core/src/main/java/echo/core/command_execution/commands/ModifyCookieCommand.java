package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;

/**
 * Returns the name value pair for a cookie.
 */
public class ModifyCookieCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="ModifyCookieCommand"/> class.
     *
     * @param log   The logger.
     * @param name  Name of the cookie.
     * @param value Value of the cookie.
     */
    public ModifyCookieCommand(ILog log, String name, String value) {
        this(new ParameterObject(log, "Executing ModifyCookieCommand"), new WebCommandInitializer());
        getParameterObject().getWeb().setCookieName(name);
        getParameterObject().getWeb().setValue(value);
    }

    /**
     * Initializes a new instance of the <see cref="ModifyCookieCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public ModifyCookieCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.ModifyCookie(getParameterObject());
    }
}
