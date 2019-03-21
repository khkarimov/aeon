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
 * Asserts that a select has all the given options and in the order provided. Can optionally be passed an option group which will be
 * searched instead of the entire select. The two methods for searching through the options are either by their value or their visible text.
 */
public class HasOptionsInOrderCommand extends WebControlCommand {

    private String[] options;
    private String optgroup;
    private WebSelectOption select;

    /**
     * Initializes a new instance of the {@link HasOptionsInOrderCommand}.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the select should have, in the same descending order as they appear in the array.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsInOrderCommand(IByWeb selector, ICommandInitializer commandInitializer, String[] options, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("HasOptionsInOrderCommand_Info"), Arrays.toString(options), selector), selector, commandInitializer);
        this.options = options;
        this.select = select;
    }

    /**
     * Initializes a new instance of the {@link HasOptionsInOrderCommand}.
     *
     * @param selector           The selector.
     * @param commandInitializer The command initializer.
     * @param options            The options that the option group should have, in the same descending order as they appear in the array.
     * @param optgroup           The label of the option group that will be searched.
     * @param select             The way the options will be searched, either WebSelectOption.Text or WebSelectOption.Value.
     */
    public HasOptionsInOrderCommand(IByWeb selector, ICommandInitializer commandInitializer, String[] options, String optgroup, WebSelectOption select) {
        super(String.format(Locale.getDefault(), Resources.getString("CheckCommand_Info"), selector), selector, commandInitializer);
        this.options = options;
        this.select = select;
        this.optgroup = optgroup;
    }

    /**
     * Provides the logic for the command.
     *
     * @param driver  The web driver.
     * @param control The web control.
     */
    @Override
    protected void commandDelegate(IWebDriver driver, WebControl control) {
        driver.hasOptionsInOrder(control, options, optgroup, select);
    }
}
