package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IWebDriver;

/**
 * <p>Appends a query string to the current URL in the browser.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.AppendQueryString("QueryString");</p>
 */
public class AppendQueryStringCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="AppendQueryStringCommand"/> class.
     *
     * @param log         The logger.
     * @param queryString The query string to append to the current url.
     */
    public AppendQueryStringCommand(ILog log, String queryString) {
        this(new ParameterObject(log, String.format(Resources.getString("AppendQueryStringCommand_Info"), queryString)), new WebCommandInitializer());
        getParameterObject().getWeb().setQueryString(queryString);
    }

    /**
     * Initializes a new instance of the <see cref="AppendQueryStringCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public AppendQueryStringCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The current handler after the change.
     */
    @Override
    protected Object CommandDelegate(IWebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("driver");
        }

        return driver.GoToUrl(getParameterObject());
    }
}
