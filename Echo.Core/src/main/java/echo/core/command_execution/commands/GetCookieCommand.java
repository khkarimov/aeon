package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

/**
 * Returns the name value pair for a cookie.
 */
public class GetCookieCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="GetCookieCommand"/> class.
     *
     * @param log  The logger.
     * @param name Name of the cookie.
     */
    public GetCookieCommand(ILog log, String name) {
        this(new ParameterObject(log, "Executing GetCookieCommand"), new WebCommandInitializer());
        getParameterObject().getWeb().setCookieName(name);
    }

    /**
     * Initializes a new instance of the <see cref="GetCookieCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public GetCookieCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     * @return A list of all the cookies.
     */
    @Override
    protected Object CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        return frameworkAbstractionFacade.GetCookie(getParameterObject());
    }
}
