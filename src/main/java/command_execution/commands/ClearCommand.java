package command_execution.commands;

import command_execution.commands.initialization.ICommandInitializer;
import command_execution.commands.initialization.WebCommandInitializer;
import command_execution.commands.interfaces.ISelectorFinder;
import common.Resources;
import common.logging.ILog;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

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
     * @param frameworkAbstractionFacade the framework abstraction facade
     * @param webElement The web element
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade, WebElement webElement) {
        frameworkAbstractionFacade.ClearElement(webElement);
    }
}
