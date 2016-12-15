package echo.core.command_execution.commands.web;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.WebControl;
import echo.core.framework_abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/1/2016.
 */

/**
 * Asserts that a select element has all the given options. Can optionally be passed an option group which will be searched instead of
 * the entire select. The two methods for searching through the options are either by their value or their visible text.
 */
public class HasOptionsCommand extends WebControlCommand {
    private String[] options;
    private String optgroup;
    private WebSelectOption select;

    /**
     * Initializes a new instance of HasOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should have, either their values or texts.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String[] options, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("HasOptionsCommand_Info"), options, selector), selector, commandInitializer);
        this.options = options;
        this.optgroup = null;
        this.select = select;
    }

    /**
     * Initializes a new instance of HasOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should have, either their values or texts.
     * @param optgroup           The label of the option group that will be searched instead of the entire select.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String[] options, String optgroup, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("HasOptionsCommand_Info"), options, selector), selector, commandInitializer);
        this.options = options;
        this.optgroup = optgroup;
        this.select = select;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The webdriver.
     * @param control The webcontrol.
     */
    @Override
    protected void CommandDelegate(IWebDriver driver, WebControl control) {
        driver.HasOptions(getGuid(), control, options, optgroup, select);
    }
}
