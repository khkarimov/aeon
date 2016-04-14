package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

import java.net.URL;

/**
 * <p>Verifies the current URL.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.VerifyUrl("URL");</p>
 * <p>      Context.Browser.VerifyUrl(Uri Url);</p>
 */
public class VerifyUrlCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="VerifyUrlCommand"/> class.
     *
     * @param log The logger.
     * @param url The expected url.
     */
    public VerifyUrlCommand(ILog log, URL url) {
        this(new ParameterObject(log, Resources.getString("VerifyUrlCommand_Info")), new WebCommandInitializer());
        getParameterObject().getWeb().setValue((url == null) ? "" : url.toString());
    }

    /**
     * Initializes a new instance of the <see cref="VerifyUrlCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command Initializer.
     */
    public VerifyUrlCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        frameworkAbstractionFacade.VerifyUrl(getParameterObject());
    }
}
