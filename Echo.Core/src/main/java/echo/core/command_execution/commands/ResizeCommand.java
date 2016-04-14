package echo.core.command_execution.commands;

import com.sun.glass.ui.Size;
import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;

/**
 * <p>Resizes the currently focused browser window.</p>
 * <p></p>
 * <p>Usage:</p>
 * <p>      Context.Browser.Resize(BrowserSize.Maximized);</p>
 * <p>      Context.Browser.Resize(800, 600);</p>
 */
public class ResizeCommand extends Command {
    /**
     * Initializes a new instance of the <see cref="ResizeCommand"/> class.
     *
     * @param log  The logger.
     * @param size The new browser size.
     */
    public ResizeCommand(ILog log, Size size) {
        this(new ParameterObject(log, Resources.getString("ResizeCommand_Info")), new WebCommandInitializer());
        getParameterObject().getWeb().setSize(size);
    }

    /**
     * Initializes a new instance of the <see cref="ResizeCommand"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    public ResizeCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
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

        frameworkAbstractionFacade.Resize(getParameterObject());
    }
}
