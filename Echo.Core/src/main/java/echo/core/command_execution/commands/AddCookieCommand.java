package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.ICookie;
import echo.core.framework_abstraction.IDriver;

/**
 * Returns the name value pair for a cookie.
 */
public class AddCookieCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="AddCookieCommand"/> class.
     *
     * @param log    The logger.
     * @param cookie Cookie to be added.
     */
    public AddCookieCommand(ILog log, ICookie cookie) {
        this(new ParameterObject(log, "Adding cookie"), new WebCommandInitializer());
        getParameterObject().getWeb().setCookie(cookie);
    }

    /**
     * Initializes a new instance of the <see cref="AddCookieCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public AddCookieCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.AddCookie(getParameterObject());
    }
}
