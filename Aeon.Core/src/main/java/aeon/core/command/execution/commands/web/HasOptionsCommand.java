package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.Resources;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should have, either their values or texts.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsCommand(IBy selector, ICommandInitializer commandInitializer, String[] options, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("HasOptionsCommand_Info"), options, selector), selector, commandInitializer);
        this.options = options;
        this.optgroup = null;
        this.select = select;
    }

    /**
     * Initializes a new instance of HasOptionsCommand.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should have, either their values or texts.
     * @param optgroup           The label of the option group that will be searched instead of the entire select.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsCommand(IBy selector, ICommandInitializer commandInitializer, String[] options, String optgroup, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("HasOptionsCommand_Info"), options, selector), selector, commandInitializer);
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
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.hasOptions(control, options, optgroup, select);
    }
}
