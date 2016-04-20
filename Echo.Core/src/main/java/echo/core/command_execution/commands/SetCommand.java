package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.common.Resources;
import echo.core.common.web.SelectOption;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;

import java.util.Locale;

/**
 * Sets an element to a certain value.
 */
public class SetCommand extends WebElementCommand {

    /**
     * Initializes a new instance of SetCommand
     *
     * @param log             the logger
     * @param switchMechanism the switch mechanism
     * @param selector        the selector
     * @param finder          the finder
     * @param selectionOption the selection option
     * @param value           the value
     */
    public SetCommand(ILog log, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder, SelectOption selectionOption, String value) {
        this(new ParameterObject(log, String.format(Locale.getDefault(), Resources.getString("SetCommand_Info"), value, selector), switchMechanism, selector, finder), new WebCommandInitializer());

        getParameterObject().getWeb().setValue(value);
        getParameterObject().getWeb().setSelectOption(selectionOption);
    }

    /**
     * Initializes a new instance of SetCommand
     *
     * @param parameterObject    Parameter object
     * @param commandInitializer The command initalizer
     */
    public SetCommand(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        super(parameterObject, commandInitializer);
    }

    /**
     * The method which providse the logic for the web element command.
     *
     * @param driver the framework abstraction facade
     */
    @Override
    protected void Command(IWebDriver driver) {
        driver.Set(getParameterObject());
    }
}
