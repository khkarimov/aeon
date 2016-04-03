package command_execution.commands;

import command_execution.commands.initialization.ICommandInitializer;
import command_execution.commands.initialization.WebCommandInitializer;
import command_execution.commands.interfaces.ISelectorFinder;
import common.SelectOption;
import common.logging.ILog;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

import java.util.Locale;

/**
 * Sets an element to a certain value.
 */
public class SetCommand extends WebElementCommand  {

    /**
     * Initializes a new instance of SetCommand
     * @param log the logger
     * @param switchMechanism  the switch mechanism
     * @param selector the selector
     * @param finder the finder
     * @param selectionOption the selection option
     * @param value the value
     */
    public SetCommand(ILog log, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder, SelectOption selectionOption, String value) {
        this(new ParameterObject(log, String.format(Locale.getDefault(), CommandsInfo.Get_SetCommandInfo(), value, selector), switchMechanism, selector, finder), new WebCommandInitializer());

        getParameterObject().getWeb().setValue(value);
        getParameterObject().getWeb().setSelectOption(selectionOption);
    }

    /**
     * Initializes a new instance of SetCommand
     * @param parameterObject Parameter object
     * @param commandInitializer The command initalizer
     */
    public SetCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which providse the logic for the web element command.
     * @param frameworkAbstractionFacade the framework abstraction facade
     * @param webElement The web element
     */
    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade, WebElement webElement) {
        frameworkAbstractionFacade.Set(getParameterObject());
    }
}
