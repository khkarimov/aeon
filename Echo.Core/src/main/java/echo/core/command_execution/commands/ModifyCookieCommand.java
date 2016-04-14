package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

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
     * @param frameworkAbstractionFacade The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        frameworkAbstractionFacade.ModifyCookie(getParameterObject());
    }
}
