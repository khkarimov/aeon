package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.CompareType;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/16/2016.
 */

/**
 * Asserts that a select element has all of its options in lexicographic order. Can either specify the options are ascending or descending by alphanumeric order, comparing either their value or
 * text.
 */
public class HasAllOptionsInOrderCommand extends WebControlCommand {

    private CompareType compare;
    private String optGroup;

    /**
     * Initializes a new instance of the HasAllOptionsInOrderCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param compare            The way that all the options in the select element will be compared.
     * @param optGroup           the optional option group that would be searched in isolation instead of the entire select.
     */
    public HasAllOptionsInOrderCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, CompareType compare, String optGroup) {
        super(log, String.format(Locale.getDefault(), Resources.getString("HasAllOptionsInOrderCommand_Info"), selector), selector, commandInitializer);
        this.compare = compare;
        this.optGroup = optGroup;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control Thw web control.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.HasAllOptionsInOrder(getGuid(), control, compare, optGroup);
    }
}
