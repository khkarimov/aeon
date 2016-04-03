package command_execution.commands;

import command_execution.commands.initialization.ICommandInitializer;
import command_execution.commands.initialization.WebCommandInitializer;
import command_execution.commands.interfaces.ISelectorFinder;
import common.logging.ILog;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import framework_abstraction.WebElement;

import java.util.Locale;

/**
 * Clicks an element
 */
public class ClickCommand extends WebElementCommand {

    /**
     * Initializes a new instance of Click Command
     * @param log
     * @param switchMechanism
     * @param selector
     * @param finder
     */
    public ClickCommand(ILog log, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        this(new ParameterObject(log,
                    String.format(Locale.getDefault(), CommandsInfo.Get_ClickCommandInfo(), selector),
                    switchMechanism,
                    selector,
                    finder),
                new WebCommandInitializer());
    }

    /**
     * Initializes a new instance of Click Command
     * @param parameterObject the parameter object
     * @param commandInitializer the command initializer
     */
    public ClickCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    @Override
    protected void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade, WebElement webElement) {
        if(frameworkAbstractionFacade == null) {
            throw new IllegalArgumentException();
        }
        frameworkAbstractionFacade.ClickElement(getParameterObject());
    }
}
