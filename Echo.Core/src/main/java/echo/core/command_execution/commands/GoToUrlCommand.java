package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IFrameworkAbstractionFacade;

import java.net.URL;

/**
 * <p>Navigate the currently focused browser to the URL provided.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.GoToUrl("url String")</p>
 * <p>      Context.Browser.GoToUrl("url String", setMainWindowBoolean)</p>
 * <p>      Context.Browser.GoToUrl(<see cref="Uri"/>)</p>
 * <p>      Context.Browser.GoToUrl(<see cref="Uri"/>, setMainWindowBoolean)</p>
 */
public class GoToUrlCommand extends CommandWithReturn {
    /**
     * Initializes a new instance of the <see cref="GoToUrlCommand"/> class.
     *
     * @param log The logger.
     * @param url The title of the window.
     */
    public GoToUrlCommand(ILog log, URL url) {
        super(new ParameterObject(log, String.format(Resources.getString("GoToUrlCommand_Info"), url)), new WebCommandInitializer());
        getParameterObject().getWeb().setUrl(url);
    }

    /**
     * Initializes a new instance of the <see cref="GoToUrlCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public GoToUrlCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     * @return The current handler after the change.
     */
    @Override
    protected Object CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException("frameworkAbstractionFacade");
        }

        return frameworkAbstractionFacade.GoToUrl(getParameterObject());
    }
}
