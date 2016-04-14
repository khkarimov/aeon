package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebElementCommand extends Command {

    /**
     * Initialized a new instance of Web Element Command
     *
     * @param log             the logger
     * @param message         the message to the log
     * @param switchMechanism the switch mechanism
     * @param selector        the selector
     * @param finder          the finder
     */
    protected WebElementCommand(ILog log, String message, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        this(new ParameterObject(log, message, switchMechanism, selector, finder), new WebCommandInitializer());
    }


    /**
     * Initialized a new instance of Web Element Command
     *
     * @param log     the logger
     * @param message the message to the log
     */
    protected WebElementCommand(ILog log, String message) {
        this(new ParameterObject(log, message), new WebCommandInitializer());
    }

    /**
     * Initialized a new instance of Web Element Command
     *
     * @param parameterObject    framework param object
     * @param commandInitializer the command initializer
     */
    protected WebElementCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command
     *
     * @param driver The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IDriver driver) {
        getCommandInitializer().FindElement(driver, getParameterObject());
        Command(driver);
    }

    /**
     * The method which provides the logic for the web element command
     *  @param driver the framework abstraction facade
     *
     */
    protected abstract void Command(IDriver driver);
}
