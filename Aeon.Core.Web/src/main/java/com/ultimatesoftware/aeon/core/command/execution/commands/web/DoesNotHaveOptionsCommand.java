package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;

import java.util.Arrays;
import java.util.Locale;

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
     * Initializes a new instance of {@link DoesNotHaveOptionsCommand} class.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should not have, either their values or texts.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public DoesNotHaveOptionsCommand(IByWeb selector, ICommandInitializer commandInitializer, String[] options, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("DoesNotHaveOptionsCommand_Info"), Arrays.toString(options), selector), selector, commandInitializer);
        this.options = options;
        this.optgroup = null;
        this.select = select;
    }

    /**
     * Initializes a new instance of DoesNotHaveOptionsCommand.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should not have, either their values or texts.
     * @param optgroup           The label of the option group that will be searched instead of the entire select.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public DoesNotHaveOptionsCommand(IByWeb selector, ICommandInitializer commandInitializer, String[] options, String optgroup, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("DoesNotHaveOptionsCommand_Info"), Arrays.toString(options), selector), selector, commandInitializer);
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
        driver.doesNotHaveOptions(control, options, optgroup, select);
    }
}
