package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * Returns the name value pair for a cookie.
 */
public class DeleteCookieCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="DeleteCookieCommand"/> class.
     *
     * @param log  The logger.
     * @param name Name of the cookie.
     */
    public DeleteCookieCommand(ILog log, String name) {
        this(new ParameterObject(log, "Deleting cookie: " + name), new WebCommandInitializer());
        getParameterObject().getWeb().setCookieName(name);
    }

    /**
     * Initializes a new instance of the <see cref="DeleteCookieCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public DeleteCookieCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.DeleteCookie(getParameterObject());
    }
}
