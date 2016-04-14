package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

public class GetAllCookiesCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="GetAllCookiesCommand"/> class.
     *
     * @param log The logger.
     */
    public GetAllCookiesCommand(ILog log) {
        this(new ParameterObject(log, "Getting all cookies"), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of the <see cref="GetAllCookiesCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public GetAllCookiesCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        return frameworkAbstractionFacade.GetAllCookies(getParameterObject());
    }
}