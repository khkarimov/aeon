package command_execution.commands;

import command_execution.commands.initialization.ICommandInitializer;
import command_execution.commands.initialization.WebCommandInitializer;
import command_execution.commands.interfaces.ISelectorFinder;
import common.logging.ILog;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

/**
 * Serves as the base class for all web element commands that need a finder
 */
public abstract class WebElementCommand extends Command {

    /**
     * Initialized a new instance of Web Element Command
     * @param log the logger
     * @param message the message to the log
     * @param switchMechanism the switch mechanism
     * @param selector the selector
     * @param finder the finder
     */
    protected WebElementCommand(ILog log, String message, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        this(new ParameterObject(log, message, switchMechanism, selector, finder), new WebCommandInitializer());
    }


    /**
     * Initialized a new instance of Web Element Command
     * @param log the logger
     * @param message the message to the log
     */
    protected WebElementCommand(ILog log, String message) {
        this(new ParameterObject(log, message), new WebCommandInitializer());
    }

    /**
     * Initialized a new instance of Web Element Command
     * @param parameterObject framework param object
     * @param commandInitializer  the command initializer
     */
    protected WebElementCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which provides the logic for the command
     * @param frameworkAbstractionFacade The framework abstraction facade.
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade) {
        getCommandInitializer().FindElement(frameworkAbstractionFacade, getParameterObject());
        CommandDelegate(frameworkAbstractionFacade, getParameterObject().getWeb().getWebElement());
    }

    /**
     * The method which provides the logic for the web element command
     * @param frameworkAbstractionFacade the framework abstraction facade
     * @param webElement The web element
     */
    protected abstract void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade, WebElement webElement);
}
