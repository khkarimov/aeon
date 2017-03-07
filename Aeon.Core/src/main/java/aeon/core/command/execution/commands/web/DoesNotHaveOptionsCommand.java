package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.logging.ILog;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Locale;

/**
 * Created by RafaelT on 6/3/2016.
 */

/**
 * Asserts that a select element does not have any of the given options. Can optionally be passed an option group which
 * will be searched instead of the entire select. The two methods for searching through the options are either by their value or their
 * visible text.
 */
public class DoesNotHaveOptionsCommand extends WebControlCommand {
    private String[] options;
    private String optgroup;
    private WebSelectOption select;

    /**
     * Initializes a new instance of DoesNotHaveOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should not have, either their values or texts.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public DoesNotHaveOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String[] options, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DoesNotHaveOptionsCommand_Info"), options, selector), selector, commandInitializer);
        this.options = options;
        this.optgroup = null;
        this.select = select;
    }

    /**
     * Initializes a new instance of DoesNotHaveOptionsCommand.
     *
     * @param log                The logger.
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should not have, either their values or texts.
     * @param optgroup           The label of the option group that will be searched instead of the entire select.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public DoesNotHaveOptionsCommand(ILog log, IBy selector, ICommandInitializer commandInitializer, String[] options, String optgroup, WebSelectOption select) {
        super(log, String.format(Locale.getDefault(), Resources.getString("DoesNotHaveOptionsCommand_Info"), options, selector), selector, commandInitializer);
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
        driver.DoesNotHaveOptions(getGuid(), control, options, optgroup, select);
    }
}
