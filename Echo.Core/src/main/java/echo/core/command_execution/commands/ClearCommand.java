package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;

import java.util.Locale;

/**
 * Clears an element
 */
public class ClearCommand extends WebElementCommand {

    /**
     * Initializes a new instance of Clear Command
     * @param log   the logger
     * @param switchMechanism the switch mechanism
     * @param selector the selector
     * @param finder the finder
     */
    public ClearCommand(ILog log, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        this(new ParameterObject(log, String.format(Locale.getDefault(), Resources.getString("ClearCommand_Info"), selector), switchMechanism, selector, finder), new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of Clear Command
     * @param parameterObject the framework param object
     * @param commandInitializer the command initializer
     */
    public ClearCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the web element command
     * @param driver the framework abstraction facade
     *
     */
    @Override
    protected void Command(IDriver driver) {
        driver.Clear(getParameterObject());
    }
}
