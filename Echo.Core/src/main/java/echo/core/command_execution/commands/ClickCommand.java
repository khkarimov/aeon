package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IElement;

import java.util.Locale;

/**
 * Clicks an element
 */
public class ClickCommand extends WebElementCommand {

    /**
     * Initializes a new instance of Click Command
     *
     * @param log
     * @param switchMechanism
     * @param selector
     * @param finder
     */
    public ClickCommand(ILog log, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        this(new ParameterObject(log,
                        String.format(Locale.getDefault(), Resources.getString("ClickCommand_Info"), selector),
                        switchMechanism,
                        selector,
                        finder),
                new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of Click Command
     *
     * @param parameterObject    the parameter object
     * @param commandInitializer the command initializer
     */
    public ClickCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade, IElement element) {
        if (frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException();
        }
        frameworkAbstractionFacade.ClickElement(getParameterObject());
    }
}
