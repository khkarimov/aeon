package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;

/**
 * <p>Verifies the current URL.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.VerifyUrl("URL");</p>
 * <p>      Context.Browser.VerifyUrl(Uri Url);</p>
 */
public class VerifyTitleCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="VerifyUrlCommand"/> class.
     *
     * @param log The logger.
     * @param title The expected title.
     */
    public VerifyTitleCommand(ILog log, String title) {
        this(new ParameterObject(log, Resources.getString("VerifyTitleCommand")), new WebCommandInitializer());
        getParameterObject().getWeb().setValue(title);
    }

    /**
     * Initializes a new instance of the <see cref="VerifyUrlCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command Initializer.
     */
    public VerifyTitleCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        driver.VerifyTitle(getParameterObject());
    }
}
